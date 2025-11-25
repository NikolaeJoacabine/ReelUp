package com.nikol.auth_impl.data.remote.service

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

interface AuthService {
    suspend fun createGuestSession(): Either<CreateGuestSessionErrorApi, GuestSessionDetailDTO>
    suspend fun createRequestToken(): Either<CreateRequestTokenErrorApi, RequestTokenDTO>
    suspend fun validateRequestToken(credential: ValidateTokenWithLoginDTO): Either<ValidateRequestTokenErrorApi, Unit>
    suspend fun createSession(body: CreateSessionDTO): Either<CreateSessionErrorApi, SessionDTO>
}