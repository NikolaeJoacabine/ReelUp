package com.nikol.auth_impl.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestTokenDTO(
    @SerialName("expires_at")
    val expiresAt: String,
    @SerialName("request_token")
    val requestToken: String,
    @SerialName("success")
    val success: Boolean
)