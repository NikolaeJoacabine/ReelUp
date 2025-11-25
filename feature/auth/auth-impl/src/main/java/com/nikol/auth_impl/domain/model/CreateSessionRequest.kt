package com.nikol.auth_impl.domain.model

data class CreateSessionRequest(
    val login: UserLogin,
    val password: UserPassword,
    val requestToken: RequestToken
)