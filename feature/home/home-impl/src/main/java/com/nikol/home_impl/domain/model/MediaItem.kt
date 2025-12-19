package com.nikol.home_impl.domain.model

import com.nikol.ui.model.MediaType


data class MediaItem(
    val id: Int,
    val type: MediaType,
    val title: String,
    val originalTitle: String,
    val description: String,
    val rating: Double,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String?
)
