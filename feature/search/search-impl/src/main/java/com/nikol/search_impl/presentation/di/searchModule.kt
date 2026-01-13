package com.nikol.search_impl.presentation.di

import com.nikol.nav_impl.navApi.MainFeatureApi
import com.nikol.search_impl.presentation.navigation.SearchFeature
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val searchModule = module {
    singleOf(::SearchFeature) bind MainFeatureApi::class
}