package com.nikol.auth_impl.presentation.mvi.intent

import com.nikol.viewmodel.UiIntent

sealed interface CheckPageIntent : UiIntent{
    data object StartCheck : CheckPageIntent
    data object NavigateToSart : CheckPageIntent
    data object NavigateToHome : CheckPageIntent
}