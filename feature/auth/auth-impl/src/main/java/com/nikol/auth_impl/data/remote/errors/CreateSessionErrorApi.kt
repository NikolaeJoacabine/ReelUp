package com.nikol.auth_impl.data.remote.errors

sealed interface CreateSessionErrorApi {
    data object RequestTokenNotFound : CreateSessionErrorApi
    data object SessionDenied : CreateSessionErrorApi
    data object InvalidApiKey : CreateSessionErrorApi
    data class Unknow(val e: Throwable) : CreateSessionErrorApi
}