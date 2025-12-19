package com.nikol.home_impl.presentation.mvi.intent

import com.nikol.home_impl.domain.parameters.Period
import com.nikol.viewmodel.UiIntent


sealed interface MovieIntent : UiIntent {
    data object RefreshData : MovieIntent
    data class NavigateToDetail(val id: String) : MovieIntent
    data object LoadAllData : MovieIntent
    data class ChangePeriodForTrend(val period: Period) : MovieIntent
}