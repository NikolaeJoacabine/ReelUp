package com.nikol.security.repository

import com.nikol.security.model.SessionState
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun saveUserSession(sessionId: String)
    suspend fun saveGuestSession(sessionId: String, expiresAt: String)
    suspend fun clearSession()
    fun observeSessionState(): Flow<SessionState>
    suspend fun getCurrentSessionState(): SessionState

}