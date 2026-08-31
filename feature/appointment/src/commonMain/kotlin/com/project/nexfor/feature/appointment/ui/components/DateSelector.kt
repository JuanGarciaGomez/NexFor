package com.project.nexfor.feature.appointment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.project.nexfor.core.ui.theme.NexForShapes
import com.project.nexfor.core.ui.theme.NexForTheme
import com.project.nexfor.feature.appointment.model.DayUi
import kotlinx.datetime.LocalDate

@Composable
fun DateSelector(
    days: List<DayUi>,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(days, key = { it.date.toString() }) { day ->
            DateChip(
                day = day,
                isSelected = day.date == selectedDate,
                onClick = { onDateSelected(day.date) }
            )
        }
    }
}

@Composable
private fun DateChip(day: DayUi, isSelected: Boolean, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val extra = NexForTheme.extra
    val borderColor = if (isSelected) colors.primary else colors.outline
    val labelColor = if (isSelected) colors.primary else extra.mutedForeground
    val numberColor = if (isSelected) colors.primary else colors.onSurface

    Column(
        modifier = Modifier
            .width(56.dp)
            .clip(NexForShapes.medium)
            .background(colors.surface)
            .border(1.5.dp, borderColor, NexForShapes.medium)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = day.dayLabel,
            style = MaterialTheme.typography.labelSmall,
            color = labelColor
        )
        Text(
            text = "${day.dayNumber}",
            style = MaterialTheme.typography.titleMedium,
            color = numberColor
        )
    }
}