package com.nikol.auth_impl.presentation.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.nikol.auth_api.Auth
import com.nikol.auth_impl.presentation.di.AuthComponent
import com.nikol.auth_impl.presentation.di.StartPageComponent
import com.nikol.auth_impl.presentation.ui.screen.StartScreen
import com.nikol.di.scope.LinkedContext
import com.nikol.di.scope.ScopedContext
import com.nikol.nav_impl.commonDestination.MainGraph

fun NavGraphBuilder.authFeature(navController: NavController) {
    composable<Auth> {
        ScopedContext<AuthComponent> {
            val nestedNavController = rememberNavController()
            NavHost(
                navController = nestedNavController,
                startDestination = StartPage,
                modifier = Modifier.fillMaxSize()
            ) {
                composable<StartPage> {
                    LinkedContext<StartPageComponent> {
                        StartScreen(
                            onMain = {
                                navController.navigate(MainGraph) {
                                    popUpTo(0) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}