package com.nikol.auth_impl.data.remote.errors

sealed interface CreateRequestTokenErrorApi {
    data object InvalidApiKey : CreateRequestTokenErrorApi
    data class Unknow(val e: Throwable) : CreateRequestTokenErrorApi
}