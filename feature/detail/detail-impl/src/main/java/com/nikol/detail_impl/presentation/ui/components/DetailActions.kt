package com.nikol.detail_impl.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nikol.detail_impl.R

@Composable
fun PrimaryActionsRow(
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onPlayTrailer: () -> Unit,
    onMore: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onPlayTrailer,
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(Icons.Rounded.PlayArrow, null)
            Spacer(Modifier.width(8.dp))
            Text(
                stringResource(R.string.detail_trailer),
                style = MaterialTheme.typography.titleMedium
            )
        }

        FilledTonalIconToggleButton(
            checked = isFavorite,
            onCheckedChange = { onToggleFavorite() },
            modifier = Modifier.size(56.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Icon(if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder, null)
        }

        FilledTonalIconButton(
            onClick = onMore,
            modifier = Modifier.size(56.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Icon(Icons.Rounded.MoreVert, null)
        }
    }
}