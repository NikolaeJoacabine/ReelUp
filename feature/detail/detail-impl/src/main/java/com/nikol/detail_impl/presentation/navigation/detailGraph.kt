package com.nikol.detail_impl.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nikol.detail_api.DetailScreen
import com.nikol.detail_impl.presentation.di.DetailComponent
import com.nikol.detail_impl.presentation.ui.screens.DetailScreenUi
import com.nikol.di.scope.ScopedContext

internal fun NavGraphBuilder.detailGraph(navController: NavController) {
    composable<DetailScreen> {
        ScopedContext<DetailComponent> {
            DetailScreenUi(
                onBack = { navController.popBackStack() }
            )
        }
    }
}