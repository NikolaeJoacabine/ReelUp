package com.nikol.home_impl.presentation.ui.comonents

import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.nikol.ui.model.MediaType

@Composable
internal fun HomeTopBar(
    selectedType: MediaType,
    onTabSelected: (MediaType) -> Unit
) {
    PrimaryTabRow(selectedTabIndex = selectedType.ordinal) {
        Tab(
            selected = selectedType == MediaType.MOVIE,
            onClick = { onTabSelected(MediaType.MOVIE) },
            text = { Text(MediaType.MOVIE.name) }
        )
        Tab(
            selected = selectedType == MediaType.TV,
            onClick = { onTabSelected(MediaType.TV) },
            text = { Text(MediaType.TV.name) }
        )
    }
}
