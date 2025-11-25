package com.nikol.auth_impl.domain.model

@JvmInline
value class RequestToken(val token: String)

@JvmInline
value class SessionToken(val token: String)

@JvmInline
value class SessionGuestToken(val token: String)
