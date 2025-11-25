package com.nikol.auth_impl.domain.useCase

import arrow.core.raise.either
import com.nikol.auth_impl.domain.errors.CreateSessionError
import com.nikol.auth_impl.domain.model.CreateSessionRequest
import com.nikol.auth_impl.domain.parameters.UserCredential
import com.nikol.auth_impl.domain.repository.AuthRepository
import com.nikol.auth_impl.domain.repository.ResponseSessionToken
import com.nikol.domainutil.BaseResponseError
import com.nikol.domainutil.UseCase
import kotlinx.coroutines.Dispatchers

class CreateSessionUseCase(
    private val authRepository: AuthRepository
) : UseCase<UserCredential, ResponseSessionToken>(Dispatchers.IO) {
    override suspend fun run(params: UserCredential): ResponseSessionToken = either {
        val requestToken = authRepository.getRequestToken()
            .mapLeft {
                when (it){
                    is BaseResponseError.Error -> CreateSessionError.Error
                    BaseResponseError.NoInternet -> CreateSessionError.Error
                    BaseResponseError.Unauthenticated -> CreateSessionError.Error
                }
            }.bind()
        authRepository.createSession(
            CreateSessionRequest(
                login = params.login,
                password = params.password,
                requestToken = requestToken
            )
        ).bind()
    }
}
