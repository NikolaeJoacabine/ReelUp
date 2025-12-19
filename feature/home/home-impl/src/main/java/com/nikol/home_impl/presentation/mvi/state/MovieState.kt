package com.nikol.home_impl.presentation.mvi.state

import androidx.compose.runtime.Immutable
import com.nikol.home_impl.domain.parameters.Period
import com.nikol.ui.model.Content
import com.nikol.ui.state.ListState
import com.nikol.viewmodel.UiState

@Immutable
data class MovieState(
    val isLoading: Boolean,
    val trend: TrendContent,
    val nowPlaying: ListState<Content>
) : UiState

@Immutable
data class TrendContent(
    val state: ListState<Content>,
    val period: Period
)
