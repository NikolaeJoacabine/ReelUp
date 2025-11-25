package com.nikol.auth_impl.domain.errors

sealed interface CreateSessionError {
    data object InvalidCredential : CreateSessionError
    data object Error : CreateSessionError
}