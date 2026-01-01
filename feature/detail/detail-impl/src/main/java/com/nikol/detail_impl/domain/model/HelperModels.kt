package com.nikol.detail_impl.domain.model

import com.nikol.detail_api.ContentType

// Модель человека (Актер или Режиссер)
data class PersonDomain(
    val id: Int,
    val name: String,
    val photoUrl: String?,
    val role: String // "Director", "Actor - Character Name"
)

// Модель Видео (Трейлер)
data class VideoDomain(
    val id: String,
    val key: String, // YouTube ID
    val name: String,
    val type: String // "Trailer", "Teaser"
)

// Модель Сезона (Только для сериалов)
data class SeasonDomain(
    val id: Int,
    val number: Int,
    val name: String,
    val episodeCount: Int,
    val posterUrl: String?,
    val airDate: String?
)

// Модель для списка рекомендаций (краткая версия)
data class ContentSummaryDomain(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val rating: Double,
    val type: ContentType
)

data class ProductionCompanyDomain(
    val name: String,
    val logoUrl: String?
)