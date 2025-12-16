package com.nikol.home_impl.presentation.di

import com.nikol.home_impl.domain.useCase.GetPopularUseCase
import com.nikol.home_impl.presentation.navigation.HomeFeature
import com.nikol.home_impl.presentation.viewModel.HomePageViewModel
import com.nikol.home_impl.presentation.viewModel.MovieViewModel
import com.nikol.nav_impl.navApi.MainFeatureApi
import com.nikol.nav_impl.navApi.NavApi
import com.nikol.nav_impl.scopedNavigation.Component
import com.nikol.nav_impl.scopedNavigation.component
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal class MovieComponent : Component()
internal class TVComponent : Component()
internal class HomeComponent : Component()

val movieModule = module {
    component { MovieComponent() }
    scope<MovieComponent> {
        viewModelOf(::MovieViewModel)
    }
}

val tvModule = module {
    component { TVComponent() }
    scope<TVComponent> {

    }
}

val homeModule = module {

    includes(tvModule, movieModule)

    singleOf(::HomeFeature) bind MainFeatureApi::class

    component { HomeComponent() }
    scope<HomeComponent> {
        factoryOf(::GetPopularUseCase)
        viewModelOf(::HomePageViewModel)
    }
}