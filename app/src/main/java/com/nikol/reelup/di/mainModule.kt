package com.nikol.reelup.di

import com.nikol.auth_impl.presentation.di.authModule
import com.nikol.detail_impl.presentation.di.detailModule
import com.nikol.home_impl.presentation.di.homeModule
import com.nikol.network.di.networkModule
import com.nikol.security.di.securityModule
import org.koin.dsl.module

val mainModule = module {
    includes(networkModule, securityModule)
    includes(authModule, homeModule, detailModule)
}