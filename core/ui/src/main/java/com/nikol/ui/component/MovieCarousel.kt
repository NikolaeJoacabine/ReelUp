package com.nikol.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikol.ui.model.Content
import com.nikol.ui.preview.MovieCard
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MoviesCarousel(
    movies: ImmutableList<Content>,
    onItemClick: (Content) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp)
    ) {
        items(
            items = movies,
            key = { it.uniqueKey }
        ) { movie ->
            MovieCard(movie = movie, onClick = { onItemClick(movie) })
        }
    }
}