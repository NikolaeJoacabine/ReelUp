package com.nikol.home_impl.presentation.mvi.intent

import androidx.compose.runtime.Immutable
import com.nikol.ui.model.MediaType
import com.nikol.viewmodel.UiIntent


sealed interface HomeIntent : UiIntent {
    data class ChangeTypeContent(val mediaType: MediaType) : HomeIntent
}