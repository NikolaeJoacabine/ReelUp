package com.nikol.auth_impl.data.remote.errors

sealed interface ValidateRequestTokenErrorApi {
    data object InvalidRequestToken : ValidateRequestTokenErrorApi
    data object InvalidApiKey : ValidateRequestTokenErrorApi

    data object InvalidCredential : ValidateRequestTokenErrorApi
    data class Unknow(val e: Throwable) : ValidateRequestTokenErrorApi
}