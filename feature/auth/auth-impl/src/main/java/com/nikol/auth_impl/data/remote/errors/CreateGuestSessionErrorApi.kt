package com.nikol.auth_impl.data.remote.errors

sealed interface CreateGuestSessionErrorApi {
    data object InvalidApiKey : CreateGuestSessionErrorApi
    data class Unknow(val e: Throwable) : CreateGuestSessionErrorApi
}