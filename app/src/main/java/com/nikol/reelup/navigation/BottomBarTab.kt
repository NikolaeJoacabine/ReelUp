package com.nikol.reelup.navigation

import androidx.annotation.DrawableRes
import com.nikol.home_api.destination.HomeGraph

sealed class BottomBarTab<T : Any>(val route: T, val icon: Int) {
    data object Home : BottomBarTab<HomeGraph>(route = HomeGraph, icon = 0)
}