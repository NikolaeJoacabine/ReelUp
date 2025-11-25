package com.nikol.auth_impl.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuestSessionDetailDTO(
    @SerialName("expires_at")
    val expiresAt: String,
    @SerialName("guest_session_id")
    val guestSessionId: String,
    @SerialName("success")
    val success: Boolean?
)