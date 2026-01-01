package com.nikol.nav_impl.navApi

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface NavApi {
    fun registerFeature(
        navController: NavController,
        navGraphBuilder: NavGraphBuilder,
        secondNavController: NavController? = null,
        modifier: Modifier = Modifier
    )
}

interface RootFeatureApi : NavApi

interface MainFeatureApi : NavApi
