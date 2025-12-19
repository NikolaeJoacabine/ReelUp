package com.nikol.home_impl.presentation.mvi.intent

import com.nikol.home_impl.domain.parameters.Period
import com.nikol.viewmodel.UiIntent

sealed interface TVIntent : UiIntent {
    data object RefreshData : TVIntent
    data class NavigateToDetail(val id: String) : TVIntent
    data object LoadAllData : TVIntent
    data class ChangePeriodForTrend(val period: Period) : TVIntent
}