package com.nikol.detail_impl.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonDTO(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("overview") val overview: String?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("season_number") val seasonNumber: Int,
    @SerialName("episode_count") val episodeCount: Int,
    @SerialName("air_date") val airDate: String? = null,
    @SerialName("vote_average") val voteAverage: Double
)