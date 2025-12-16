package com.nikol.auth_impl.domain.errors

sealed interface CheckUserError {
    data object UserNotAuth : CheckUserError
}