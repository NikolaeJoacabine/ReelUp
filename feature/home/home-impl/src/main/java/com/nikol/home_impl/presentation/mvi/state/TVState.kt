package com.nikol.home_impl.presentation.mvi.state

import androidx.compose.runtime.Immutable
import com.nikol.viewmodel.UiState

@Immutable
data class TVState(
    val isLoading: Boolean,
    val trend: TrendContent
) : UiState