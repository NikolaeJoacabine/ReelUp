package com.nikol.auth_impl.presentation.mvi.state

import com.nikol.viewmodel.UiState

data class StartPageState(
    val guestButtonState: CreateSessionState,
    val sessionState: CreateSessionState,
    val login: String,
    val password: String,
    val showPassword: Boolean
) : UiState

sealed interface CreateSessionState {
    data object Error : CreateSessionState
    data object Loading : CreateSessionState
    data object Initial : CreateSessionState
}