package com.nikol.detail_impl.presentation.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.nikol.nav_impl.navApi.MainFeatureApi
import com.nikol.nav_impl.navApi.RootFeatureApi

class DetailFeature : MainFeatureApi {
    override fun registerFeature(
        navController: NavController,
        navGraphBuilder: NavGraphBuilder,
        secondNavController: NavController?,
        modifier: Modifier
    ) {
        with(navGraphBuilder) {
            detailGraph(navController)
        }
    }
}