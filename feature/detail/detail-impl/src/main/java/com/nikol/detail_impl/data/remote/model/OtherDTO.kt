package com.nikol.detail_impl.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtherDTO(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)

@Serializable
data class CreditsDTO(
    @SerialName("cast") val cast: List<CastDTO>,
    @SerialName("crew") val crew: List<CrewDTO>
)

@Serializable
data class CastDTO(
    val id: Int,
    val name: String,
    @SerialName("profile_path") val profilePath: String?,
    @SerialName("character") val character: String
)

@Serializable
data class CrewDTO(
    val id: Int,
    val name: String,
    @SerialName("job") val job: String // Ищем "Director"
)

@Serializable
data class VideosDTO(
    val results: List<VideoResultDTO>
)

@Serializable
data class VideoResultDTO(
    val id: String,
    val key: String, // YouTube ID
    val name: String,
    val site: String, // "YouTube"
    val type: String // "Trailer", "Teaser"
)

@Serializable
data class TrendingDTO(
    @SerialName("id") val id: Int,
    @SerialName("media_type") val mediaType: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("original_name") val originalName: String? = null,
    @SerialName("first_air_date") val firstAirDate: String? = null,
    @SerialName("overview") val overview: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null
)

@Serializable
data class RecommendationsDTO(
    val results: List<TrendingDTO>
)

@Serializable
data class AccountStatesDTO(
    val favorite: Boolean = false,
    val watchlist: Boolean = false
)
