package com.nikol.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class VideoUi(
    val key: String, // YouTube ID (для запуска интента/плеера)
    val name: String
)