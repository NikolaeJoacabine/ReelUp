package com.nikol.auth_impl.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionDTO(
    @SerialName("session_id")
    val sessionId: String,
    @SerialName("success")
    val success: Boolean
)