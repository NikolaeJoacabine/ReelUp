package com.nikol.security.di

import com.nikol.security.dataStore.SecurityDataStoreService
import com.nikol.security.keyStore.KeyStoreManager
import com.nikol.security.repository.TokenRepository
import com.nikol.security.repository.TokenRepositoryImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val securityModule = module {
    singleOf(::KeyStoreManager)
    singleOf(::SecurityDataStoreService)
    factoryOf(::TokenRepositoryImpl) bind TokenRepository::class
}