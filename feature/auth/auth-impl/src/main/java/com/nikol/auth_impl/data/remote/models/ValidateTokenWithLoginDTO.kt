package com.nikol.auth_impl.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValidateTokenWithLoginDTO(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("request_token")
    val requestToken: String
)