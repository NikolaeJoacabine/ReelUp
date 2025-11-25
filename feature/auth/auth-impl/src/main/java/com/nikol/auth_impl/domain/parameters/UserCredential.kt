package com.nikol.auth_impl.domain.parameters

import com.nikol.auth_impl.domain.model.UserLogin
import com.nikol.auth_impl.domain.model.UserPassword

data class UserCredential(
    val login: UserLogin,
    val password: UserPassword
)