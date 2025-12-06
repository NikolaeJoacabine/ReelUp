package com.nikol.reelup.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.nikol.home_api.destination.HomeGraph

sealed class BottomBarTab<T : Any>(
    val route: T,
    val icon: ImageVector,
    val title: String
) {
    data object Home : BottomBarTab<HomeGraph>(
        route = HomeGraph,
        icon = Icons.Rounded.Home,
        title = "Home"
    )
}