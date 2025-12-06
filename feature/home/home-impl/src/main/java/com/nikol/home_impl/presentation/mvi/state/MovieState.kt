package com.nikol.home_impl.presentation.mvi.state

import com.nikol.viewmodel.UiState

data class MovieState(
    val isLoading: Boolean
) : UiState