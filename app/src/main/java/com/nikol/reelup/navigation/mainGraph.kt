package com.nikol.reelup.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
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
import kotlinx.serialization.Serializable

@Serializable
data object SearchGraph

@Serializable
data object ProfileGraph

enum class MainTab(
    val route: Any,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Home(HomeGraph, "Home", androidx.compose.material.icons.Icons.Default.Home),
    Search(SearchGraph, "Search", androidx.compose.material.icons.Icons.Default.Search),
    Profile(ProfileGraph, "Profile", androidx.compose.material.icons.Icons.Default.Person)
}

fun NavGraphBuilder.mainGraph(
    rootNavController: NavController,
    mainFeatures: List<MainFeatureApi>
) {
    composable<MainGraph> {

        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = backStackEntry?.destination

        val currentTab = remember(currentDestination) {
            MainTab.entries.firstOrNull { tab ->
                currentDestination?.hierarchy?.any {
                    it.route == tab.route::class.qualifiedName
                } == true
            } ?: MainTab.Home
        }

        Scaffold(
            bottomBar = {
                MainGraphBottomBar(
                    tabs = MainTab.entries,
                    currentTab = currentTab,
                    onTabClick = { tab ->
                        if (tab == currentTab) {
                            navController.popBackStack(
                                route = tab.route,
                                inclusive = false
                            )
                        } else {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = HomeGraph,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = innerPadding.calculateBottomPadding())
            ) {

                mainFeatures.forEach {
                    it.registerFeature(
                        navController,
                        this,
                        rootNavController,
                        modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                    )
                }

                composable<SearchGraph> {
                    Text("Search")
                }

                composable<ProfileGraph> {
                    Text("Profile")
                }
            }
        }
    }
}

@Composable
fun MainGraphBottomBar(
    tabs: List<MainTab>,
    currentTab: MainTab,
    onTabClick: (MainTab) -> Unit
) {
    BottomAppBar {
        tabs.forEach { tab ->
            NavigationBarItem(
                selected = tab == currentTab,
                onClick = { onTabClick(tab) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title
                    )
                },
                label = { Text(tab.title) }
            )
        }
    }
}
