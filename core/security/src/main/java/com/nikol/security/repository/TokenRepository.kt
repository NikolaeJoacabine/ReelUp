package com.nikol.security.repository

interface TokenRepository {
    suspend fun setToken(token: String)
    suspend fun getToken(): String
    suspend fun tokenIsExist(): Boolean
    suspend fun deleteToken()
}