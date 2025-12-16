package com.nikol.auth_impl.presentation.mvi.state

import com.nikol.viewmodel.UiState

sealed interface CheckPageState : UiState {
    data object Loading : CheckPageState
}