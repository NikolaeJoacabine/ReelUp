package com.nikol.auth_impl.presentation.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.nikol.nav_impl.navApi.NavApi
import com.nikol.nav_impl.navApi.RootFeatureApi

class AuthFeature : RootFeatureApi {
    override fun registerFeature(
        navController: NavController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.authFeature(navController)
    }
}