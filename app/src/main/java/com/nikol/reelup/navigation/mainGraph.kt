package com.nikol.reelup.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
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
import com.nikol.home_impl.presentation.navigation.homeGraph
import com.nikol.nav_impl.commonDestination.MainGraph


fun NavGraphBuilder.mainGraph(navController: NavController) {
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
                homeGraph()
            }
        }
    }
}

@Composable
fun MainGraphBottomBar(navController: NavController) {
    val mainTabs = remember { listOf(BottomBarTab.Home) }
    BottomAppBar(
        modifier = Modifier
            .height(80.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
    ) {
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
                icon = {},
                label = {}
            )
        }
    }
}
