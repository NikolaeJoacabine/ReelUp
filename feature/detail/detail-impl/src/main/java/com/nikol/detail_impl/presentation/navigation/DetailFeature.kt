package com.nikol.detail_impl.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.nikol.nav_impl.navApi.RootFeatureApi

class DetailFeature : RootFeatureApi {
    override fun registerFeature(
        navController: NavController,
        navGraphBuilder: NavGraphBuilder,
        secondNavController: NavController?
    ) {
        with(navGraphBuilder) {
            detailGraph(navController)
        }
    }
}