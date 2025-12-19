package com.nikol.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class Content(
    val id: Int,
    val type: MediaType,

    val title: String,
    val description: String,
    val rating: Double,

    val posterUrl: String?,
    val backdropUrl: String?
) {
    val uniqueKey: String
        get() = "${type.name}_$id"

    val ratingText: String
        get() = String.format("%.1f", rating)
}

enum class MediaType {
    MOVIE,
    TV,
    PERSON
}