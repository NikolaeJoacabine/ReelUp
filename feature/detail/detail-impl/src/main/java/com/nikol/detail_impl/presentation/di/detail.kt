package com.nikol.detail_impl.presentation.di

import com.nikol.detail_impl.presentation.navigation.DetailFeature
import com.nikol.nav_impl.navApi.RootFeatureApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val detailModule = module {
    singleOf(::DetailFeature) bind RootFeatureApi::class
}