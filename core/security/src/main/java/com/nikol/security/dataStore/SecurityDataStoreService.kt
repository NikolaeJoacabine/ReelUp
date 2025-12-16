package com.nikol.security.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nikol.security.model.RawSessionData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class SecurityDataStoreService(
    private val context: Context
) {
    val rawSessionData: Flow<RawSessionData> = context.dataStore.data.map { prefs ->
        RawSessionData(
            userSessionId = prefs[USER_SESSION_KEY],
            guestSessionId = prefs[GUEST_SESSION_KEY],
            guestExpiresAt = prefs[GUEST_EXPIRES_AT_KEY]
        )
    }

    suspend fun saveUserSession(sessionId: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_SESSION_KEY] = sessionId
            prefs.remove(GUEST_SESSION_KEY)
            prefs.remove(GUEST_EXPIRES_AT_KEY)
        }
    }

    suspend fun saveGuestSession(sessionId: String, expiresAt: String) {
        context.dataStore.edit { prefs ->
            prefs[GUEST_SESSION_KEY] = sessionId
            prefs[GUEST_EXPIRES_AT_KEY] = expiresAt
            prefs.remove(USER_SESSION_KEY)
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }

    private companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "security_data_store")
        val USER_SESSION_KEY = stringPreferencesKey("user_session_id")
        val GUEST_SESSION_KEY = stringPreferencesKey("guest_session_id")
        val GUEST_EXPIRES_AT_KEY = stringPreferencesKey("guest_expires_at")
    }
}
