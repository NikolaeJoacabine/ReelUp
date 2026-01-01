package com.nikol.detail_impl.domain.model

data class TvDomain(
    override val id: Int,
    override val title: String,
    override val originalTitle: String,
    override val description: String,
    override val posterUrl: String?,
    override val backdropUrl: String?,
    override val rating: Double,
    override val genres: List<String>,
    override val releaseDate: String?,
    override val status: String,
    override val cast: List<PersonDomain>,
    override val trailers: List<VideoDomain>,
    override val recommendations: List<ContentSummaryDomain>,
    override val isFavorite: Boolean,
    override val isInWatchlist: Boolean,
    override val productionCompanies: List<ProductionCompanyDomain>,

    val lastAirDate: String?,
    val seasons: List<SeasonDomain>,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
) : ContentDetailDomain