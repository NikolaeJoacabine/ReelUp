package com.nikol.auth_impl.data.remote.service

import android.util.Log
import arrow.core.Either
import com.nikol.auth_impl.data.remote.errors.CreateGuestSessionErrorApi
import com.nikol.auth_impl.data.remote.errors.CreateRequestTokenErrorApi
import com.nikol.auth_impl.data.remote.errors.CreateSessionErrorApi
import com.nikol.auth_impl.data.remote.errors.ValidateRequestTokenErrorApi
import com.nikol.auth_impl.data.remote.models.CreateSessionDTO
import com.nikol.auth_impl.data.remote.models.GuestSessionDetailDTO
import com.nikol.auth_impl.data.remote.models.RequestTokenDTO
import com.nikol.auth_impl.data.remote.models.SessionDTO
import com.nikol.auth_impl.data.remote.models.ValidateTokenWithLoginDTO
import com.nikol.auth_impl.data.remote.service.route.Auth
import com.nikol.network.extensions.catchKtor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.accept
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode


class AuthServiceImpl(
    val httpClient: HttpClient
) : AuthService {

    override suspend fun createGuestSession(): Either<CreateGuestSessionErrorApi, GuestSessionDetailDTO> =
        catchKtor(
            block = {
                httpClient.get(Auth.GuestSession.New()) {
                    accept(ContentType.Application.Json)
                }.body()
            },
            errorMapper = { _, status, throwable ->
                when (status) {
                    HttpStatusCode.Unauthorized -> CreateGuestSessionErrorApi.InvalidApiKey
                    else -> CreateGuestSessionErrorApi.Unknow(throwable)
                }
            }
        )

    override suspend fun createRequestToken(): Either<CreateRequestTokenErrorApi, RequestTokenDTO> =
        catchKtor(
            block = {
                httpClient.get(Auth.Token.New()) {
                    accept(ContentType.Application.Json)
                }.body()
            },
            errorMapper = { _, status, throwable ->
                when (status) {
                    HttpStatusCode.Unauthorized -> CreateRequestTokenErrorApi.InvalidApiKey
                    else -> CreateRequestTokenErrorApi.Unknow(throwable)
                }
            }
        )

    override suspend fun validateRequestToken(
        credential: ValidateTokenWithLoginDTO
    ): Either<ValidateRequestTokenErrorApi, Unit> =
        catchKtor(
            block = {
                httpClient.post(Auth.Token.ValidateWithLogin()) {
                    accept(ContentType.Application.Json)
                    setBody(credential)
                }.body()
            },
            errorMapper = { error, status, throwable ->
                when (status) {
                    HttpStatusCode.Unauthorized -> when (error?.statusCode) {
                        30 -> ValidateRequestTokenErrorApi.InvalidCredential
                        33 -> ValidateRequestTokenErrorApi.InvalidRequestToken
                        else -> ValidateRequestTokenErrorApi.InvalidApiKey
                    }

                    else -> ValidateRequestTokenErrorApi.Unknow(throwable)
                }
            }
        )


    override suspend fun createSession(
        body: CreateSessionDTO
    ): Either<CreateSessionErrorApi, SessionDTO> =
        catchKtor(
            block = {
                httpClient.post(Auth.Session.New()) {
                    setBody(body)
                    accept(ContentType.Application.Json)
                }.body()
            },
            errorMapper = { _, status, throwable ->
                when (status) {
                    HttpStatusCode.Unauthorized -> CreateSessionErrorApi.SessionDenied
                    HttpStatusCode.NotFound -> CreateSessionErrorApi.RequestTokenNotFound
                    else -> CreateSessionErrorApi.Unknow(throwable)
                }
            }
        )
}