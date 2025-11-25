package com.nikol.network.di

import com.nikol.network.coilLoader.provideImageLoader
import com.nikol.network.httpClient.provideHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.get
import org.koin.dsl.module

val networkModule = module {
    single { provideHttpClient() }
    single { provideImageLoader(androidContext(), get()) }
}