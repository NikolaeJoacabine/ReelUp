package com.nikol.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class SeasonUi(
    val id: Int,
    val title: String,       // "Season 1"
    val episodeCount: String, // "8 Episodes"
    val posterUrl: String?
)