package com.nikol.detail_impl.presentation.mvi.effect

import com.nikol.viewmodel.UiEffect

sealed interface DetailEffect : UiEffect {
    data class SeeTrailerOnYouTube(val key: String) : DetailEffect
}