package com.nikol.auth_impl.domain.useCase


import arrow.core.raise.either
import com.nikol.auth_impl.domain.repository.AuthRepository
import com.nikol.auth_impl.domain.repository.ResponseSessionGuestToken
import com.nikol.domainutil.UseCase
import kotlinx.coroutines.Dispatchers

class CreateGuestSessionUseCase(
    private val authRepository: AuthRepository
) : UseCase<Unit, ResponseSessionGuestToken>(Dispatchers.IO) {
    override suspend fun run(params: Unit): ResponseSessionGuestToken = either {
        authRepository.createGuestSession().bind()
    }
}
