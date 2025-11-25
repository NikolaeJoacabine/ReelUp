package com.nikol.security.repository

import com.nikol.security.dataStore.SecurityDataStoreService
import com.nikol.security.keyStore.KeyStoreManager

internal class TokenRepositoryImpl(
    private val keyStoreManager: KeyStoreManager,
    private val securityDataStoreService: SecurityDataStoreService
) : TokenRepository {
    override suspend fun setToken(token: String) {
        val encrypted = keyStoreManager.encrypt(token)
        securityDataStoreService.saveToken(encrypted)
    }

    override suspend fun getToken(): String {
        val token = securityDataStoreService.getToken()
        val decryptedToken = keyStoreManager.decrypt(token ?: "")
        return decryptedToken
    }


    override suspend fun tokenIsExist(): Boolean {
        return securityDataStoreService.getToken() != null
    }

    override suspend fun deleteToken() {
        securityDataStoreService.deleteToken()
    }
}
