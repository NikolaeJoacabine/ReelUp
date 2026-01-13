package com.nikol.detail_impl.presentation.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikol.detail_api.ContentType
import com.nikol.detail_api.DetailScreen
import com.nikol.detail_impl.R
import com.nikol.detail_impl.presentation.ui.model.DetailContent
import com.nikol.ui.component.ContentSection
import com.nikol.ui.model.MediaType
import com.nikol.ui.state.ListState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContentBody(
    content: DetailContent,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onMore: () -> Unit,
    clickDetail: (detailScreen: DetailScreen) -> Unit,
    toggleInfo: () -> Unit,
    clickSeeTrailer: () -> Unit
) {
    val scrollState = rememberScrollState()

    PullToRefreshBox(
        isRefreshing = isLoading,
        onRefresh = onRefresh
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            HeaderSection(content, scrollState)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                PrimaryActionsRow(
                    isFavorite = content.isFavorite,
                    onToggleFavorite = { },
                    onPlayTrailer = clickSeeTrailer,
                    onMore = onMore,
                    trailerEnable = content.trailers.isNotEmpty()
                )

                Spacer(modifier = Modifier.height(24.dp))


                if (content.description.isNotEmpty()) {
                    SectionTitle(stringResource(R.string.detail_storyline))
                    var isOverflowing by remember { mutableStateOf(false) }

                    Text(
                        text = content.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                        lineHeight = 22.sp,
                        maxLines = if (content.showAllDescription) Int.MAX_VALUE else 4,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = {
                            if (!content.showAllDescription) {
                                isOverflowing = it.hasVisualOverflow
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .animateContentSize()
                    )

                    if (isOverflowing || content.showAllDescription) {
                        TextButton(
                            onClick = toggleInfo,
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = stringResource(if (content.showAllDescription) R.string.detail_show_less else R.string.detail_show_more)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = if (content.showAllDescription) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                if (content.genres.isNotEmpty()) {
                    SectionTitle("Жанры")
                    GenreFlowSection(genres = content.genres)
                }

                if (content.seasons.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    SectionTitle(stringResource(R.string.detail_seasons))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(content.seasons) { season ->
                            SeasonItem(season)
                        }
                    }
                }

                if (content.cast.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    SectionTitle(stringResource(R.string.detail_cast))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(content.cast) { person ->
                            PersonItem(person)
                        }
                        item {
                            TextButton(onClick = { }) {
                                Text(stringResource(R.string.detail_see_all))
                            }
                        }
                    }
                }

                if (content.recommendations.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    ContentSection(
                        title = stringResource(R.string.detail_recommendations),
                        state = ListState.Success(content.recommendations),
                        onItemClick = { rec ->
                            val type = when (rec.type) {
                                MediaType.MOVIE -> ContentType.MOVIE
                                MediaType.TV -> ContentType.TV
                                MediaType.PERSON -> ContentType.PERSON
                            }
                            clickDetail(DetailScreen(contentType = type, id = rec.id))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}