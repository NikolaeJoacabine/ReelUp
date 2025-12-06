package com.nikol.home_impl.presentation.mvi.intent

import com.nikol.viewmodel.UiIntent

sealed interface MovieIntent : UiIntent {
    data object RefreshData : MovieIntent
    data class NavigateToDetail(val id: String) : MovieIntent
}