package com.nikol.home_impl.presentation.mvi.intent

import com.nikol.home_impl.domain.parameters.TypeContent
import com.nikol.viewmodel.UiIntent

sealed interface HomeIntent : UiIntent {
    data class ChangeTypeContent(val typeContent: TypeContent) : HomeIntent
}