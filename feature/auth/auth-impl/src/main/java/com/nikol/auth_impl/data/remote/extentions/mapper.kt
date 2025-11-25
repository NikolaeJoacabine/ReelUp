package com.nikol.auth_impl.data.remote.extentions

import com.nikol.auth_impl.data.remote.models.ValidateTokenWithLoginDTO
import com.nikol.auth_impl.domain.model.CreateSessionRequest

fun CreateSessionRequest.toData(): ValidateTokenWithLoginDTO {
    return ValidateTokenWithLoginDTO(
        username = login.login,
        password = password.password,
        requestToken = requestToken.token
    )
}