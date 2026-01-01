package com.nikol.detail_impl.presentation.ui.model

import androidx.compose.runtime.Immutable
import com.nikol.detail_api.ContentType
import com.nikol.ui.model.Content
import com.nikol.ui.model.PersonUi
import com.nikol.ui.model.SeasonUi
import com.nikol.ui.model.VideoUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


@Immutable
data class DetailContent(
    val id: Int,
    val type: ContentType,

    val title: String,
    val originalTitle: String,
    val description: String,
    val posterUrl: String?,
    val backdropUrl: String?,

    val ratingText: String,
    val releaseYear: String,
    val genres: ImmutableList<String>,
    val metaInfo: String,

    val cast: ImmutableList<PersonUi>,
    val trailers: ImmutableList<VideoUi>,
    val recommendations: ImmutableList<Content>,

    val seasons: ImmutableList<SeasonUi> = emptyList<SeasonUi>().toImmutableList(),

    val isFavorite: Boolean,
    val isInWatchImmutableList: Boolean,

    val showAllDescription: Boolean = false,

    val productionLogos: ImmutableList<String>,
    val runtimeText: String?,
    val statusText: String,
)