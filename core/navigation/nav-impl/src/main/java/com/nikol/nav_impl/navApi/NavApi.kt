package com.nikol.nav_impl.navApi

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface NavApi {
    fun registerFeature(
        navController: NavController,
        navGraphBuilder: NavGraphBuilder,
        secondNavController: NavController? = null
    )
}

interface RootFeatureApi : NavApi

interface MainFeatureApi : NavApi
