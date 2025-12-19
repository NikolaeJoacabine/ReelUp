package com.nikol.home_impl.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nikol.di.scope.LinkedContext
import com.nikol.di.scope.ScopedContext
import com.nikol.di.scope.viewModelWithRouter
import com.nikol.home_api.destination.HomeGraph
import com.nikol.home_impl.presentation.di.HomeComponent
import com.nikol.home_impl.presentation.di.MovieComponent
import com.nikol.home_impl.presentation.di.TVComponent
import com.nikol.home_impl.presentation.mvi.intent.HomeIntent
import com.nikol.home_impl.presentation.ui.comonents.HomeTopBar
import com.nikol.home_impl.presentation.ui.screens.MovieScreen
import com.nikol.home_impl.presentation.ui.screens.TVScreen
import com.nikol.home_impl.presentation.viewModel.HomePageViewModel
import com.nikol.home_impl.presentation.viewModel.TypeContentRouter
import com.nikol.ui.model.MediaType

private fun createTypeContentRouter(navController: NavHostController): TypeContentRouter =
    object : TypeContentRouter {
        private fun navigateTab(route: Any) =
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

        override fun navigateToMovie() = navigateTab(MoviePage)
        override fun navigateToTV() = navigateTab(TVPage)
    }


fun NavGraphBuilder.homeGraph(rootNavController: NavController) {
    composable<HomeGraph> {
        ScopedContext<HomeComponent> {
            val contentNavController = rememberNavController()
            val homeViewModel = viewModelWithRouter<HomePageViewModel, TypeContentRouter> {
                createTypeContentRouter(contentNavController)
            }

            val state by homeViewModel.uiState.collectAsStateWithLifecycle()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    HomeTopBar(state.mediaType) { typeContent ->
                        homeViewModel.setIntent(HomeIntent.ChangeTypeContent(typeContent))
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = contentNavController,
                    startDestination = MoviePage,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = innerPadding.calculateTopPadding())
                ) {
                    composable<MoviePage> {
                        LinkedContext<MovieComponent> {
                            MovieScreen()
                        }
                    }
                    composable<TVPage> {
                        LinkedContext<TVComponent> {
                            TVScreen {
                                homeViewModel.setIntent(HomeIntent.ChangeTypeContent(MediaType.MOVIE))
                            }
                        }
                    }
                }
            }
        }
    }
}