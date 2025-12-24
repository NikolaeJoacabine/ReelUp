package com.nikol.detail_impl.presentation.mvi.state

import androidx.compose.runtime.Immutable
import com.nikol.detail_api.ContentType
import com.nikol.ui.state.SingleState
import com.nikol.viewmodel.UiState


data class DetailState(
    val contentType: ContentType,
    val state: SingleState<Content>,
    val isLoading: Boolean
) : UiState

@Immutable
data class Content(
    val id: Int
)