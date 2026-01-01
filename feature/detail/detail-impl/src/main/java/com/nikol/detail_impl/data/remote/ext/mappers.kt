package com.nikol.detail_impl.data.remote.ext

import com.nikol.detail_api.ContentType
import com.nikol.detail_impl.data.remote.model.CastDTO
import com.nikol.detail_impl.data.remote.model.MovieDetailDTO
import com.nikol.detail_impl.data.remote.model.ProductionCompanyDTO
import com.nikol.detail_impl.data.remote.model.SeasonDTO
import com.nikol.detail_impl.data.remote.model.TrendingDTO
import com.nikol.detail_impl.data.remote.model.TvDetailDTO
import com.nikol.detail_impl.data.remote.model.VideoResultDTO
import com.nikol.detail_impl.domain.model.ContentSummaryDomain
import com.nikol.detail_impl.domain.model.MovieDomain
import com.nikol.detail_impl.domain.model.PersonDomain
import com.nikol.detail_impl.domain.model.ProductionCompanyDomain
import com.nikol.detail_impl.domain.model.SeasonDomain
import com.nikol.detail_impl.domain.model.TvDomain
import com.nikol.detail_impl.domain.model.VideoDomain

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
private const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w1280"
private const val LOGO_BASE_URL = "https://image.tmdb.org/t/p/w300"

fun MovieDetailDTO.toDomain(): MovieDomain {
    return MovieDomain(
        id = id,
        title = title,
        originalTitle = originalTitle,
        description = overview,
        posterUrl = posterPath?.let { "$IMAGE_BASE_URL$it" },
        backdropUrl = backdropPath?.let { "$BACKDROP_BASE_URL$it" },
        rating = voteAverage,
        genres = genres.map { it.name },
        releaseDate = releaseDate,
        status = status,
        cast = credits?.cast?.map { it.toDomain() } ?: emptyList(),
        trailers = videos?.results?.mapNotNull { it.toDomain() } ?: emptyList(),
        recommendations = recommendations?.results?.map { it.toDomainSummary(ContentType.MOVIE) }
            ?: emptyList(),
        isFavorite = accountStates?.favorite ?: false,
        isInWatchlist = accountStates?.watchlist ?: false,
        runtime = runtime,
        productionCompanies = productionCompanies.map { it.toDomain() }
    )
}

fun TvDetailDTO.toDomain(): TvDomain {
    return TvDomain(
        id = id,
        title = name,
        originalTitle = originalName,
        description = overview,
        posterUrl = posterPath?.let { "$IMAGE_BASE_URL$it" },
        backdropUrl = backdropPath?.let { "$BACKDROP_BASE_URL$it" },
        rating = voteAverage,
        genres = genres.map { it.name },
        releaseDate = firstAirDate,
        status = status,
        cast = credits?.cast?.map { it.toDomain() } ?: emptyList(),
        trailers = videos?.results?.mapNotNull { it.toDomain() } ?: emptyList(),
        recommendations = recommendations?.results?.map { it.toDomainSummary(ContentType.TV) }
            ?: emptyList(),
        isFavorite = accountStates?.favorite ?: false,
        isInWatchlist = accountStates?.watchlist ?: false,
        lastAirDate = lastAirDate,
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
        seasons = seasons.map { it.toDomain() },
        productionCompanies = productionCompanies.map { it.toDomain() }
    )
}


fun CastDTO.toDomain() = PersonDomain(
    id = id,
    name = name,
    photoUrl = profilePath?.let { "$IMAGE_BASE_URL$it" },
    role = character
)

fun VideoResultDTO.toDomain(): VideoDomain? {
    if (site != "YouTube") return null
    return VideoDomain(
        id = id,
        key = key,
        name = name,
        type = type
    )
}

fun SeasonDTO.toDomain() = SeasonDomain(
    id = id,
    number = seasonNumber,
    name = name,
    episodeCount = episodeCount,
    posterUrl = posterPath?.let { "$IMAGE_BASE_URL$it" },
    airDate = airDate
)

fun TrendingDTO.toDomainSummary(type: ContentType) = ContentSummaryDomain(
    id = id,
    title = title ?: name ?: "Unknown",
    posterUrl = posterPath?.let { "$IMAGE_BASE_URL$it" },
    rating = voteAverage ?: 0.0,
    type = type
)

fun ProductionCompanyDTO.toDomain() = ProductionCompanyDomain(
    name = name,
    logoUrl = logoPath?.let { "$LOGO_BASE_URL$it" }
)