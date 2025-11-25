package com.nikol.security.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class SecurityDataStoreService(
    private val context: Context
) {

    suspend fun saveToken(token: String?) {
        context.dataStore.edit {
            it[TOKEN_KEY] = token ?: ""
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.map {
            it[TOKEN_KEY]
        }.first()
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    private companion object Companion {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)
        val TOKEN_KEY = stringPreferencesKey("token_key")
        const val DATA_STORE_NAME = "security_data_store"
    }

}