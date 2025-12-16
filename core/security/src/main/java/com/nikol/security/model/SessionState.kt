package com.nikol.security.model

sealed interface SessionState {
    data class User(val sessionId: String) : SessionState
    data class Guest(val sessionId: String, val expiresAt: String) : SessionState
    object None : SessionState
}