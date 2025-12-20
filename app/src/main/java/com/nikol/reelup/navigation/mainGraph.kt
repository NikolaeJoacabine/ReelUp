package com.nikol.reelup.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nikol.di.scope.viewModelWithRouter
import com.nikol.home_api.destination.HomeGraph
import com.nikol.nav_impl.commonDestination.MainGraph
import com.nikol.nav_impl.navApi.MainFeatureApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable

@Serializable
data object SearchGraph

@Serializable
data object ProfileGraph

fun NavGraphBuilder.mainGraph(
    rootNavController: NavController,
    mainFeatures: List<MainFeatureApi>
) {
    composable<MainGraph> {
        val nestedNavController = rememberNavController()

        val viewModel = viewModelWithRouter<MainViewModel, MainRouter> {
            MainRouterImpl(nestedNavController)
        }

        val state by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(nestedNavController) {
            nestedNavController.currentBackStackEntryFlow.collectLatest { entry ->
                val destination = entry.destination
                val activeTab = MainTab.entries.find { tab ->
                    destination.hierarchy.any { it.hasRoute(tab.route::class) }
                }
                if (activeTab != null && activeTab != state.currentTab) {
                    viewModel.setIntent(MainIntent.SyncTab(activeTab))
                }
            }
        }


        Scaffold(
            bottomBar = {
                MainGraphBottomBar(
                    tabs = state.tabs,
                    currentTab = state.currentTab,
                    onTabClick = { tab ->
                        viewModel.setIntent(MainIntent.OnTabSelected(tab))
                    }
                )
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
                    it.registerFeature(nestedNavController, this, rootNavController)
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
            val isSelected = tab == currentTab

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabClick(tab) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title
                    )
                },
                label = { Text(tab.title) },
            )
        }
    }
}