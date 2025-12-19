package com.nikol.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikol.ui.model.Content
import com.nikol.ui.shimmer.ShimmerMovie
import com.nikol.ui.state.ListState

@Composable
fun ContentStateBox(
    state: ListState<Content>,
    onItemClick: (Content) -> Unit,
    modifier: Modifier = Modifier,
    loadingContent: (@Composable () -> Unit)? = null,
    errorContent: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is ListState.Loading -> {
                if (loadingContent != null) {
                    loadingContent()
                } else {
                    DefaultLoadingState()
                }
            }

            is ListState.Error -> {
                if (errorContent != null) {
                    errorContent()
                } else {
                    DefaultErrorState()
                }
            }

            is ListState.Success -> {
                MoviesCarousel(movies = state.content, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
private fun DefaultLoadingState() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(5) {
            ShimmerMovie()
        }
    }
}

@Composable
private fun DefaultErrorState() {
    Box(modifier = Modifier.height(270.dp), contentAlignment = Alignment.Center) {
        Text(text = "Failed to load", color = MaterialTheme.colorScheme.error)
    }
}