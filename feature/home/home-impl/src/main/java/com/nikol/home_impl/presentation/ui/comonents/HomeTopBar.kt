package com.nikol.home_impl.presentation.ui.comonents

import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.nikol.home_impl.domain.parameters.TypeContent

@Composable
internal fun HomeTopBar(
    selectedType: TypeContent,
    onTabSelected: (TypeContent) -> Unit
) {
    PrimaryTabRow(selectedTabIndex = selectedType.ordinal) {
        TypeContent.entries.forEach { type ->
            Tab(
                selected = selectedType == type,
                onClick = { onTabSelected(type) },
                text = { Text(type.name) }
            )
        }
    }
}
