package com.nikol.nav_impl.navApi

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface NavApi {
    fun registerFeature(navController: NavController, navGraphBuilder: NavGraphBuilder)
}

interface RootFeatureApi : NavApi

interface MainFeatureApi : NavApi
