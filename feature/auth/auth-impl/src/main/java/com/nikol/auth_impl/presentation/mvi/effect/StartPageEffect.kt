package com.nikol.auth_impl.presentation.mvi.effect

import com.nikol.viewmodel.UiEffect
import com.nikol.viewmodel.UiState

sealed interface StartPageEffect : UiEffect {
    data object GoToBrowser : StartPageEffect
}