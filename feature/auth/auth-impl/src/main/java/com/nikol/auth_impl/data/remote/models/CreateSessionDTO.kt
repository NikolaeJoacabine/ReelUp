package com.nikol.auth_impl.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionDTO(
    @SerialName("request_token")
    val requestToken: String
)