package com.nikol.home_impl.presentation.mvi.state

import androidx.compose.runtime.Immutable
import com.nikol.ui.model.MediaType
import com.nikol.viewmodel.UiState

@Immutable
data class HomeState(
    val mediaType: MediaType
) : UiState
