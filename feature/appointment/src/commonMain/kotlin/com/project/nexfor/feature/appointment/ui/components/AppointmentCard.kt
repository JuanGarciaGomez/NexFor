package com.project.nexfor.feature.appointment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp
import com.project.nexfor.core.ui.theme.NexForShapes
import com.project.nexfor.feature.appointment.model.AppointmentUi
import com.project.nexfor.feature.appointment.model.accentColor
import kotlinx.datetime.LocalTime

@Composable
fun AppointmentCard(
    appointment: AppointmentUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val accent = appointment.status.accentColor()
    val containerColor = accent.copy(alpha = 0.14f).compositeOver(colors.background)

    Row(
        modifier = modifier
            .clip(NexForShapes.medium)
            .background(containerColor)
            .border(1.dp, accent.copy(alpha = 0.5f), NexForShapes.medium)
            .clickable(onClick = onClick)
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .width(3.dp)
                .background(accent)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = appointment.clientName,
                style = MaterialTheme.typography.titleMedium,
                color = colors.onBackground
            )
            Text(
                text = appointment.service,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSurfaceVariant
            )
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Schedule,
                    contentDescription = null,
                    tint = colors.onSurfaceVariant,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "${formatTime(appointment.startTime)} – ${formatTime(appointment.endTime)}",
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.onSurfaceVariant
                )
            }
        }
    }
}

private fun formatTime(time: LocalTime): String {
    val h12 = when {
        time.hour == 0 -> 12
        time.hour > 12 -> time.hour - 12
        else -> time.hour
    }
    val suffix = if (time.hour < 12) "AM" else "PM"
    return "$h12:${time.minute.toString().padStart(2, '0')} $suffix"
}