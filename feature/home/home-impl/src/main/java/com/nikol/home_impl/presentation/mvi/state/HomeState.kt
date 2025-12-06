package com.nikol.home_impl.presentation.mvi.state

import com.nikol.home_impl.domain.parameters.TypeContent
import com.nikol.viewmodel.UiState

data class HomeState(
    val typeContent: TypeContent
) : UiState
