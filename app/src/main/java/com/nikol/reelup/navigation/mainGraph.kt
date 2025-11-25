package com.nikol.reelup.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nikol.home_api.destination.HomeGraph
import com.nikol.nav_impl.commonDestination.MainGraph


fun NavGraphBuilder.mainGraph(navController: NavController) {
    composable<MainGraph> {
        val nestedNavController = rememberNavController()
        Scaffold(
            bottomBar = {

            }
        ) { innerPadding ->
            NavHost(
                navController = nestedNavController,
                startDestination = HomeGraph,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

            }
        }
    }
}
