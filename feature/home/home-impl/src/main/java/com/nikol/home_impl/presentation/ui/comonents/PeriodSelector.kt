package com.nikol.home_impl.presentation.ui.comonents

import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nikol.home_impl.domain.parameters.Period
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeriodSelector(
    currentPeriod: Period,
    onPeriodChanged: (Period) -> Unit
) {
    val options = persistentListOf(Period.Day, Period.Week)

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.height(32.dp)
    ) {
        options.forEachIndexed { index, period ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = { onPeriodChanged(period) },
                selected = period == currentPeriod,
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    activeContentColor = MaterialTheme.colorScheme.onPrimary,
                    inactiveContainerColor = Color.Transparent,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    text = if (period == Period.Day) "Day" else "Week",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}