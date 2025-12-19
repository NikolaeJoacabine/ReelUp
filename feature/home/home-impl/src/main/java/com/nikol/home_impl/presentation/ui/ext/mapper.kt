package com.nikol.home_impl.presentation.ui.ext

import com.nikol.home_impl.domain.model.MediaItem
import com.nikol.ui.model.Content

fun MediaItem.toUi(): Content {
    return Content(
        id = id,
        type = type,
        title = title,
        description = description,
        rating = rating,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
    )
}