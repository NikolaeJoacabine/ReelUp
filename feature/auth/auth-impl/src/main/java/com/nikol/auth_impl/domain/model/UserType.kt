package com.nikol.auth_impl.domain.model

sealed interface UserType {
    data object User : UserType
    data class Guest(val expired: String) : UserType
}