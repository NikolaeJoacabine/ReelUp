package com.nikol.home_impl.presentation.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.nikol.nav_impl.navApi.MainFeatureApi

class HomeFeature : MainFeatureApi {
    override fun registerFeature(
        navController: NavController,
        navGraphBuilder: NavGraphBuilder,
        secondNavController: NavController?,
        modifier: Modifier
    ) {
        navGraphBuilder.homeGraph(navController, modifier)
    }
}