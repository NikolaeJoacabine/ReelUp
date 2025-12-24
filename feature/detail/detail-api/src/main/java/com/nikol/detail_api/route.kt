package com.nikol.detail_api

import kotlinx.serialization.Serializable

enum class ContentType {
    MOVIE,
    TV,
    PERSON
}

@Serializable
data class DetailScreen(
    val contentType: ContentType,
    val id: Int
)