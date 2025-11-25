package com.nikol.auth_impl.data.repository

import arrow.core.raise.either
import com.nikol.auth_impl.data.remote.errors.CreateGuestSessionErrorApi
import com.nikol.auth_impl.data.remote.errors.CreateRequestTokenErrorApi
import com.nikol.auth_impl.data.remote.errors.CreateSessionErrorApi
import com.nikol.auth_impl.data.remote.errors.ValidateRequestTokenErrorApi
import com.nikol.auth_impl.data.remote.extentions.toData
import com.nikol.auth_impl.data.remote.models.CreateSessionDTO
import com.nikol.auth_impl.data.remote.service.AuthService
import com.nikol.auth_impl.domain.errors.CreateSessionError
import com.nikol.auth_impl.domain.model.CreateSessionRequest
import com.nikol.auth_impl.domain.model.RequestToken
import com.nikol.auth_impl.domain.model.SessionGuestToken
import com.nikol.auth_impl.domain.model.SessionToken
import com.nikol.auth_impl.domain.repository.AuthRepository
import com.nikol.auth_impl.domain.repository.ResponseRequestToken
import com.nikol.auth_impl.domain.repository.ResponseSessionGuestToken
import com.nikol.auth_impl.domain.repository.ResponseSessionToken
import com.nikol.domainutil.BaseResponseError
import com.nikol.domainutil.ErrorMessage
import com.nikol.security.repository.TokenRepository

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val tokenRepository: TokenRepository
) : AuthRepository {
    override suspend fun getRequestToken(): ResponseRequestToken =
        authService.createRequestToken().mapLeft { error ->
            when (error) {
                CreateRequestTokenErrorApi.InvalidApiKey -> BaseResponseError.Error(ErrorMessage(""))
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
                        ValidateRequestTokenErrorApi.InvalidApiKey -> CreateSessionError.Error
                        ValidateRequestTokenErrorApi.InvalidRequestToken -> CreateSessionError.Error
                        is ValidateRequestTokenErrorApi.Unknow -> CreateSessionError.Error
                        ValidateRequestTokenErrorApi.InvalidCredential -> CreateSessionError.InvalidCredential
                    }
                }.bind()
            val body = CreateSessionDTO(requestToken = createSessionRequest.requestToken.token)
            val token = authService.createSession(body).mapLeft { error ->
                when (error) {
                    CreateSessionErrorApi.InvalidApiKey -> CreateSessionError.Error
                    CreateSessionErrorApi.RequestTokenNotFound -> CreateSessionError.Error
                    CreateSessionErrorApi.SessionDenied -> CreateSessionError.Error
                    is CreateSessionErrorApi.Unknow -> CreateSessionError.Error
                }
            }.map { (sessionId, _) -> SessionToken(sessionId) }.bind()

            tokenRepository.setToken(token.token)
            token
        }

    override suspend fun createGuestSession(): ResponseSessionGuestToken = either {
        val token = authService.createGuestSession().mapLeft {
            when (it) {
                CreateGuestSessionErrorApi.InvalidApiKey -> BaseResponseError.Error(ErrorMessage(""))
                is CreateGuestSessionErrorApi.Unknow -> BaseResponseError.Error(ErrorMessage(""))
            }
        }.bind()
        tokenRepository.setToken(token = token.guestSessionId)
        SessionGuestToken(token = token.guestSessionId)
    }
}
