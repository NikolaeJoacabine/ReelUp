package com.nikol.detail_impl.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TvDetailDTO(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("original_name") val originalName: String,
    @SerialName("overview") val overview: String,
    @SerialName("tagline") val tagline: String?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("first_air_date") val firstAirDate: String? = null,
    @SerialName("last_air_date") val lastAirDate: String? = null,
    @SerialName("status") val status: String,
    @SerialName("episode_run_time") val episodeRunTime: List<Int> = emptyList(),
    @SerialName("number_of_episodes") val numberOfEpisodes: Int,
    @SerialName("number_of_seasons") val numberOfSeasons: Int,
    @SerialName("seasons") val seasons: List<SeasonDTO>,
    @SerialName("genres") val genres: List<OtherDTO>,
    @SerialName("production_companies") val productionCompanies: List<ProductionCompanyDTO> = emptyList(),
    @SerialName("credits") val credits: CreditsDTO? = null,
    @SerialName("videos") val videos: VideosDTO? = null,
    @SerialName("recommendations") val recommendations: RecommendationsDTO? = null,
    @SerialName("account_states") val accountStates: AccountStatesDTO? = null
)

