package com.nikol.domainutil

sealed interface BaseResponseError {
    data object Unauthenticated : BaseResponseError
    data object NoInternet : BaseResponseError
    data class Error(val message: ErrorMessage) : BaseResponseError
}