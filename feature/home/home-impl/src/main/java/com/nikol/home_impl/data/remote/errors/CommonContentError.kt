package com.nikol.home_impl.data.remote.errors

sealed interface CommonContentError {
    data object NetworkError : CommonContentError
    data object InvalidApiKey : CommonContentError
    data object InvalidParams : CommonContentError
    data object ServerError : CommonContentError
    data object RateLimitExceeded : CommonContentError
    data class Unknown(val message: String? = null) : CommonContentError
}