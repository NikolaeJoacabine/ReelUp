package com.nikol.security.repository

import com.nikol.security.dataStore.SecurityDataStoreService
import com.nikol.security.keyStore.KeyStoreManager
import com.nikol.security.model.RawSessionData
import com.nikol.security.model.SessionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

internal class TokenRepositoryImpl(
    private val keyStoreManager: KeyStoreManager,
    private val dataStore: SecurityDataStoreService
) : TokenRepository {
    override suspend fun saveUserSession(sessionId: String) {
        val encrypted = keyStoreManager.encrypt(sessionId)
        dataStore.saveUserSession(encrypted)
    }

    override suspend fun saveGuestSession(sessionId: String, expiresAt: String) {
        val encrypted = keyStoreManager.encrypt(sessionId)
        dataStore.saveGuestSession(encrypted, expiresAt)
    }

    override suspend fun clearSession() {
        dataStore.clearAll()
    }

    override fun observeSessionState(): Flow<SessionState> = dataStore.rawSessionData.map { raw ->
        mapRawToState(raw)
    }

    override suspend fun getCurrentSessionState(): SessionState {
        val raw = dataStore.rawSessionData.first()
        return mapRawToState(raw)
    }

    private fun mapRawToState(raw: RawSessionData): SessionState {
        val userEnc = raw.userSessionId
        val guestEnc = raw.guestSessionId
        val guestExpire = raw.guestExpiresAt
        return when {
            !userEnc.isNullOrBlank() -> {
                val decrypted = keyStoreManager.decrypt(userEnc)
                if (decrypted.isNotBlank()) SessionState.User(decrypted) else SessionState.None
            }

            !guestEnc.isNullOrBlank() && !guestExpire.isNullOrBlank() -> {
                val decrypted = keyStoreManager.decrypt(guestEnc)
                if (decrypted.isNotBlank() && !isSessionExpired(guestExpire)) {
                    SessionState.Guest(decrypted, guestExpire)
                } else {
                    SessionState.None
                }
            }

            else -> SessionState.None
        }
    }

    private fun isSessionExpired(expiresAt: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'")

            val expiryInstant = LocalDateTime.parse(expiresAt, formatter)
                .atZone(ZoneId.of("UTC"))
                .toInstant()

            val now = Instant.now()
            now.isAfter(expiryInstant)
        } catch (_: Exception) {
            true
        }
    }
}
