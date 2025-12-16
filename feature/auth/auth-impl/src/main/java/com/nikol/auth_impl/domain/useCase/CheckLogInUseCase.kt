package com.nikol.auth_impl.domain.useCase

import com.nikol.auth_impl.domain.repository.AuthRepository
import com.nikol.auth_impl.domain.repository.ResponseIfUserIsLogin
import com.nikol.domainutil.UseCase
import kotlinx.coroutines.Dispatchers

class CheckLogInUseCase(
    private val repository: AuthRepository
) : UseCase<Unit, ResponseIfUserIsLogin>(Dispatchers.IO) {
    override suspend fun run(params: Unit): ResponseIfUserIsLogin {
        return repository.checkIfUserIsLogIn()
    }
}