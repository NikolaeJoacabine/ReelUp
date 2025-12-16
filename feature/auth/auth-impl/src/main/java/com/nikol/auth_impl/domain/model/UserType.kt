package com.nikol.auth_impl.domain.model

sealed interface UserType {
    data object User : UserType
    data object Guest : UserType
}