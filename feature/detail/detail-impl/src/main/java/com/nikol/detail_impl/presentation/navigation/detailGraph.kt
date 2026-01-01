package com.nikol.detail_impl.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nikol.detail_api.ContentType
import com.nikol.detail_api.DetailScreen
import com.nikol.detail_impl.presentation.di.ContentComponent
import com.nikol.detail_impl.presentation.di.DetailComponent
import com.nikol.detail_impl.presentation.di.PersonComponent
import com.nikol.detail_impl.presentation.ui.screens.DetailScreenUi
import com.nikol.di.scope.LinkedContext
import com.nikol.di.scope.ScopedContext

internal fun NavGraphBuilder.detailGraph(navController: NavController) {
    composable<DetailScreen> {
        ScopedContext<DetailComponent> {

            val type = it.toRoute<DetailScreen>().contentType
            when (type) {
                ContentType.MOVIE,
                ContentType.TV -> {
                    LinkedContext<ContentComponent> {
                        DetailScreenUi(
                            onBack = { navController.popBackStack() },
                            onDetail = { detailScreen ->
                                navController.navigate(detailScreen)
                            }
                        )
                    }
                }

                ContentType.PERSON -> {
                    LinkedContext<PersonComponent> { }
                }
            }

        }
    }
}