package com.nikol.reelup.di

import com.nikol.auth_impl.presentation.di.authModule
import com.nikol.network.di.networkModule
import com.nikol.security.di.securityModule
import org.koin.dsl.module

val mainModule = module {
    includes(networkModule, securityModule)
    includes(authModule)
}