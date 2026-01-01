package com.nikol.detail_impl.domain.errors

sealed interface DetailsError {
    data object Network : DetailsError
    data object NotFound : DetailsError
    data object ServerError : DetailsError
    data object UserNotAuth : DetailsError
    data class Unknown(val message: String? = null) : DetailsError
}