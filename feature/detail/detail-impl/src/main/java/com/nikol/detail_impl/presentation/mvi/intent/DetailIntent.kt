package com.nikol.detail_impl.presentation.mvi.intent

import com.nikol.detail_api.ContentType
import com.nikol.viewmodel.UiIntent

sealed interface DetailIntent : UiIntent {
    data class LoadAllData(val contentType: ContentType) : DetailIntent
    data object Update : DetailIntent
    data object NavigateBack : DetailIntent
}