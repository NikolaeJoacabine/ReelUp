package com.nikol.detail_impl.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDTO(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("overview") val overview: String,
    @SerialName("tagline") val tagline: String?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("runtime") val runtime: Int? = null,
    @SerialName("status") val status: String,
    @SerialName("genres") val genres: List<OtherDTO>,
    @SerialName("production_companies") val productionCompanies: List<ProductionCompanyDTO> = emptyList(),
    @SerialName("credits") val credits: CreditsDTO? = null,
    @SerialName("videos") val videos: VideosDTO? = null,
    @SerialName("recommendations") val recommendations: RecommendationsDTO? = null,
    @SerialName("account_states") val accountStates: AccountStatesDTO? = null
)