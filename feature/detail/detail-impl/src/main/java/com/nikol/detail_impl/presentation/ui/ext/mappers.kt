package com.nikol.detail_impl.presentation.ui.ext

import com.nikol.detail_api.ContentType
import com.nikol.detail_impl.domain.model.ContentDetailDomain
import com.nikol.detail_impl.domain.model.ContentSummaryDomain
import com.nikol.detail_impl.domain.model.MovieDomain
import com.nikol.detail_impl.domain.model.PersonDomain
import com.nikol.detail_impl.domain.model.SeasonDomain
import com.nikol.detail_impl.domain.model.TvDomain
import com.nikol.detail_impl.domain.model.VideoDomain
import com.nikol.detail_impl.presentation.ui.model.DetailContent
import com.nikol.ui.model.Content
import com.nikol.ui.model.MediaType
import com.nikol.ui.model.PersonUi
import com.nikol.ui.model.SeasonUi
import com.nikol.ui.model.VideoUi
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

fun ContentDetailDomain.toUi(): DetailContent {

    // 1. Формируем красивую строку длительности (только для фильмов)
    val runtimeFormatted = if (this is MovieDomain) {
        this.runtime?.let {
            val hours = it / 60
            val minutes = it % 60
            if (hours > 0) "${hours}h ${minutes}m" else "${minutes}m"
        }
    } else null

    // 2. Логика формирования строки "Meta Info" (год + доп. инфо)
    val metaString = when (this) {
        is MovieDomain -> {
            // Для фильма в мета-инфо можно просто оставить длительность, если она есть
            runtimeFormatted ?: ""
        }
        is TvDomain -> {
            // "5 Seasons • 120 Episodes"
            buildString {
                append("$numberOfSeasons Seasons")
                if (numberOfEpisodes > 0) append(" • $numberOfEpisodes Episodes")
            }
        }
    }

    // 3. Получение года (yyyy)
    val year = this.releaseDate?.take(4) ?: ""

    // 4. Обработка сезонов
    val seasonsList = if (this is TvDomain) {
        this.seasons.map { it.toUi() }.toImmutableList()
    } else {
        persistentListOf()
    }

    // 5. Логотипы компаний (берем только те, где есть картинка)
    val logos = this.productionCompanies
        .mapNotNull { it.logoUrl }
        .toImmutableList()

    val contentType = if (this is MovieDomain) ContentType.MOVIE else ContentType.TV

    return DetailContent(
        id = this.id,
        type = contentType,
        title = this.title,
        originalTitle = this.originalTitle,
        description = this.description,
        posterUrl = this.posterUrl,
        backdropUrl = this.backdropUrl,
        ratingText = String.format("%.1f", this.rating),
        releaseYear = year,
        genres = this.genres.toImmutableList(),

        metaInfo = metaString,
        runtimeText = runtimeFormatted,
        statusText = this.status,
        productionLogos = logos,

        cast = this.cast.map { it.toUi() }.take(10).toImmutableList(),
        trailers = this.trailers.map { it.toUi() }.toImmutableList(),
        recommendations = this.recommendations.map { it.toUiContent() }.toImmutableList(),
        seasons = seasonsList,
        isFavorite = this.isFavorite,
        isInWatchImmutableList = this.isInWatchlist
    )
}


fun PersonDomain.toUi() = PersonUi(
    id = id,
    name = name,
    imageUrl = photoUrl,
    role = role
)

fun VideoDomain.toUi() = VideoUi(key = key, name = name)

fun SeasonDomain.toUi() = SeasonUi(
    id = id,
    title = name,
    episodeCount = episodeCount,
    posterUrl = posterUrl
)

fun ContentSummaryDomain.toUiContent() = Content(
    id = id,
    type = when (type) {
        ContentType.MOVIE -> MediaType.MOVIE
        ContentType.TV -> MediaType.TV
        ContentType.PERSON -> MediaType.PERSON
    },
    title = title,
    description = "",
    rating = rating,
    posterUrl = posterUrl,
    backdropUrl = null,
)