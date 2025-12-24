package com.nikol.detail_impl.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nikol.detail_api.DetailScreen
import com.nikol.detail_impl.presentation.ui.screens.DetailScreenUi

internal fun NavGraphBuilder.detailGraph(navController: NavController) {
    composable<DetailScreen> {
        DetailScreenUi()
    }
}