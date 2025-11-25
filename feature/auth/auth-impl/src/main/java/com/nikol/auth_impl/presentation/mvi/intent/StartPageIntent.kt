package com.nikol.auth_impl.presentation.mvi.intent

import com.nikol.viewmodel.UiIntent

sealed interface StartPageIntent : UiIntent {
    data object CreateAccount : StartPageIntent
    data object LogIn : StartPageIntent
    data object ContinueWithGuestAccount : StartPageIntent
    data class ChangePassword(val password: String) : StartPageIntent
    data class ChangeLogin(val login: String) : StartPageIntent
    data object SwitchPasswordVisibility : StartPageIntent
}