package com.nikol.detail_impl.presentation.di

import com.nikol.detail_impl.presentation.navigation.DetailFeature
import com.nikol.detail_impl.presentation.viewModel.DetailViewModel
import com.nikol.nav_impl.navApi.RootFeatureApi
import com.nikol.nav_impl.scopedNavigation.Component
import com.nikol.nav_impl.scopedNavigation.component
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


internal class DetailComponent : Component()

val detailModule = module {
    singleOf(::DetailFeature) bind RootFeatureApi::class
    component { DetailComponent() }
    scope<DetailComponent> {
        viewModelOf(::DetailViewModel)
    }
}