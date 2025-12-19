package com.nikol.home_impl.data.remote.ext

import com.nikol.home_impl.data.remote.models.TrendingDTO
import com.nikol.home_impl.domain.model.MediaItem
import com.nikol.ui.model.MediaType

private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

fun TrendingDTO.toDomain(): MediaItem {
    val type = when (mediaType) {
        "movie" -> MediaType.MOVIE
        "tv" -> MediaType.TV
        "person" -> MediaType.PERSON
        else -> {
            if (title != null) MediaType.MOVIE else MediaType.TV
        }
    }

    return MediaItem(
        id = id,
        type = type,
        title = title ?: name ?: "Unknown Title",
        originalTitle = originalTitle ?: originalName ?: "",

        description = overview ?: "",
        rating = voteAverage ?: 0.0,

        posterUrl = posterPath?.let { "$BASE_IMAGE_URL$it" },
        backdropUrl = backdropPath?.let { "$BASE_IMAGE_URL$it" },

        releaseDate = releaseDate ?: firstAirDate
    )
}