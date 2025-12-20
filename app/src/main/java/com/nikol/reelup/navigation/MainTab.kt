package com.nikol.reelup.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.nikol.home_api.destination.HomeGraph
import com.nikol.viewmodel.BaseViewModel
import com.nikol.viewmodel.Router
import com.nikol.viewmodel.UiEffect
import com.nikol.viewmodel.UiIntent
import com.nikol.viewmodel.UiState
import com.nikol.viewmodel.intentDsl.intents
import com.nikol.viewmodel.intentDsl.onLatest

enum class MainTab(
    val title: String,
    val icon: ImageVector
) {
    Home("Home", Icons.Default.Home),
    Search("Search", Icons.Default.Search),
    Profile("Profile", Icons.Default.Person);

    val route: Any
        get() = when (this) {
            Home -> HomeGraph
            Search -> SearchGraph
            Profile -> ProfileGraph
        }
}

interface MainRouter : Router {
    fun switchTab(tab: MainTab)
    fun popToRoot(tab: MainTab)
}

class MainRouterImpl(
    private val navController: NavController
) : MainRouter {

    override fun switchTab(tab: MainTab) {
        navController.navigate(tab.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun popToRoot(tab: MainTab) {
        navController.navigate(tab.route) {
            popUpTo(tab.route) {
                inclusive = false
            }
            launchSingleTop = true
        }
    }
}

@Immutable
data class MainState(
    val currentTab: MainTab = MainTab.Home,
    val tabs: List<MainTab> = MainTab.entries
) : UiState

sealed interface MainIntent : UiIntent {
    data class OnTabSelected(val tab: MainTab) : MainIntent
    data class SyncTab(val tab: MainTab) : MainIntent

}

class MainViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<MainIntent, MainState, UiEffect, MainRouter>() {

    companion object {
        private const val TAB_KEY = "selected_tab_key"
    }

    override fun createInitialState(): MainState {
        // Восстанавливаем таб из SSH или берем Home по умолчанию
        val savedTabName = savedStateHandle.get<String>(TAB_KEY)
        val initialTab = savedTabName?.let { MainTab.valueOf(it) } ?: MainTab.Home

        return MainState(currentTab = initialTab)
    }

    override fun handleIntents() = intents {
        onLatest<MainIntent.OnTabSelected> { intent ->
            val selectedTab = intent.tab
            val currentTab = uiState.value.currentTab

            if (selectedTab == currentTab) {
                navigate { popToRoot(selectedTab) }
            } else {
                setState { copy(currentTab = selectedTab) }

                savedStateHandle[TAB_KEY] = selectedTab.name

                navigate { switchTab(selectedTab) }
            }
        }

        onLatest<MainIntent.SyncTab> { intent ->
            val actualTab = intent.tab
            if (uiState.value.currentTab != actualTab) {
                setState { copy(currentTab = actualTab) }
                savedStateHandle[TAB_KEY] = actualTab.name
            }
        }
    }
}