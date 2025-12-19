package com.nikol.auth_impl.data.repository

import android.util.Log
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import com.nikol.auth_impl.data.remote.errors.CreateGuestSessionErrorApi
import com.nikol.auth_impl.data.remote.errors.CreateRequestTokenErrorApi
import com.nikol.auth_impl.data.remote.errors.CreateSessionErrorApi
import com.nikol.auth_impl.data.remote.errors.ValidateRequestTokenErrorApi
import com.nikol.auth_impl.data.remote.extentions.toData
import com.nikol.auth_impl.data.remote.models.CreateSessionDTO
import com.nikol.auth_impl.data.remote.service.AuthService
import com.nikol.auth_impl.domain.errors.CheckUserError
import com.nikol.auth_impl.domain.errors.CreateSessionError
import com.nikol.auth_impl.domain.model.CreateSessionRequest
import com.nikol.auth_impl.domain.model.RequestToken
import com.nikol.auth_impl.domain.model.SessionGuestToken
import com.nikol.auth_impl.domain.model.SessionToken
import com.nikol.auth_impl.domain.model.UserType
import com.nikol.auth_impl.domain.repository.AuthRepository
import com.nikol.auth_impl.domain.repository.ResponseIfUserIsLogin
import com.nikol.auth_impl.domain.repository.ResponseRequestToken
import com.nikol.auth_impl.domain.repository.ResponseSessionGuestToken
import com.nikol.auth_impl.domain.repository.ResponseSessionToken
import com.nikol.domainutil.BaseResponseError
import com.nikol.domainutil.ErrorMessage
import com.nikol.security.model.SessionState
import com.nikol.security.repository.TokenRepository
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val tokenRepository: TokenRepository
) : AuthRepository {
    override suspend fun getRequestToken(): ResponseRequestToken =
        authService.createRequestToken().mapLeft { error ->
            when (error) {
                CreateRequestTokenErrorApi.InvalidApiKey,
                is CreateRequestTokenErrorApi.Unknow -> BaseResponseError.Error(ErrorMessage(""))
            }
        }.map {
            RequestToken(it.requestToken)
        }


    override suspend fun createSession(createSessionRequest: CreateSessionRequest): ResponseSessionToken =
        either {
            authService.validateRequestToken(credential = createSessionRequest.toData())
                .mapLeft { error ->
                    when (error) {
                        ValidateRequestTokenErrorApi.InvalidApiKey,
                        ValidateRequestTokenErrorApi.InvalidRequestToken,
                        is ValidateRequestTokenErrorApi.Unknow -> CreateSessionError.Error

                        ValidateRequestTokenErrorApi.InvalidCredential -> CreateSessionError.InvalidCredential
                    }
                }.bind()
            val body = CreateSessionDTO(requestToken = createSessionRequest.requestToken.token)
            val token = authService.createSession(body).mapLeft { error ->
                when (error) {
                    CreateSessionErrorApi.InvalidApiKey,
                    CreateSessionErrorApi.RequestTokenNotFound,
                    CreateSessionErrorApi.SessionDenied,
                    is CreateSessionErrorApi.Unknow -> CreateSessionError.Error
                }
            }.map { (sessionId, _) -> SessionToken(sessionId) }.bind()

            tokenRepository.saveUserSession(token.token)
            token
        }

    override suspend fun createGuestSession(): ResponseSessionGuestToken = either {
        val token = authService.createGuestSession().mapLeft {
            when (it) {
                CreateGuestSessionErrorApi.InvalidApiKey,
                is CreateGuestSessionErrorApi.Unknow -> BaseResponseError.Error(ErrorMessage(""))
            }
        }.bind()
        tokenRepository.saveGuestSession(
            sessionId = token.guestSessionId,
            expiresAt = token.expiresAt
        )
        SessionGuestToken(token = token.guestSessionId)
    }

    override suspend fun checkIfUserIsLogIn(): ResponseIfUserIsLogin {
        return when (val state = tokenRepository.getCurrentSessionState()) {
            is SessionState.User -> UserType.User.right()
            is SessionState.Guest -> {
                if (isSessionExpired(state.expiresAt)) {
                    CheckUserError.UserNotAuth.left()
                } else {
                    UserType.Guest(state.expiresAt).right()
                }
            }

            SessionState.None -> CheckUserError.UserNotAuth.left()
        }
    }

    private fun isSessionExpired(expiresAt: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'")

            val expiryInstant = LocalDateTime.parse(expiresAt, formatter)
                .atZone(ZoneId.of("UTC"))
                .toInstant()

            val now = Instant.now()
            now.isAfter(expiryInstant)
        } catch (_: Exception) {
            true
        }
    }
}
