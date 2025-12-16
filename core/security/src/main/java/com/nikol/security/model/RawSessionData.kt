package com.nikol.security.model

internal data class RawSessionData(
    val userSessionId: String?,
    val guestSessionId: String?,
    val guestExpiresAt: String?
)