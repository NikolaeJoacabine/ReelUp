package com.nikol.reelup.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nikol.home_api.destination.HomeGraph
import com.nikol.nav_impl.commonDestination.MainGraph
import com.nikol.nav_impl.navApi.MainFeatureApi


fun NavGraphBuilder.mainGraph(
    navController: NavController,
    mainFeatures: List<MainFeatureApi>
) {
    composable<MainGraph> {
        val nestedNavController = rememberNavController()
        Scaffold(
            bottomBar = {
                MainGraphBottomBar(nestedNavController)
            }
        ) { innerPadding ->
            NavHost(
                navController = nestedNavController,
                startDestination = HomeGraph,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                mainFeatures.forEach {
                    it.registerFeature(navController, this)
                }
            }
        }
    }
}

@Composable
fun MainGraphBottomBar(navController: NavController) {
    val mainTabs = remember { listOf(BottomBarTab.Home) }
    BottomAppBar {
        mainTabs.forEach { topLevelRoute ->
            val backStackEntry = navController.currentBackStackEntryAsState()
            val currentDestination = backStackEntry.value?.destination
            val selected = currentDestination?.hierarchy?.any {
                it.hasRoute(topLevelRoute.route::class)
            } == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(topLevelRoute.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = topLevelRoute.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(topLevelRoute.title)
                }
            )
        }
    }
}
