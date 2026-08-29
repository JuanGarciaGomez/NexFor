package com.project.nexfor.feature.appointment.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.nexfor.feature.appointment.model.AppointmentUi
import kotlinx.datetime.LocalTime

@Composable
fun DayTimeline(
    startHour: Int,
    endHour: Int,
    appointments: List<AppointmentUi>,
    onAppointmentClick: (AppointmentUi) -> Unit,
    onAvailableClick: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    hourHeight: Dp = 96.dp,
) {
    val colors = MaterialTheme.colorScheme
    val totalMinutes = (endHour - startHour) * 60
    val totalHeight = hourHeight * (endHour - startHour)
    val sorted = appointments.sortedBy { it.startTime }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(totalHeight)
            .padding(16.dp)
    ) {
        val labelWidth = 52.dp
        val contentStartX = labelWidth + 12.dp
        val cardWidth = maxWidth - contentStartX

        fun minutesFrom(time: LocalTime) = (time.hour * 60 + time.minute) - startHour * 60

        for (hour in startHour..endHour) {
            val y = hourHeight * (hour - startHour)
            Text(
                text = formatHour(hour),
                style = MaterialTheme.typography.labelLarge,
                color = colors.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(y = y - 8.dp)
                    .width(labelWidth)
            )
            HorizontalDivider(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = contentStartX, y = y)
                    .width(cardWidth),
                color = colors.outline,
                thickness = 1.dp
            )
        }

        var cursor = 0
        val gaps = mutableListOf<Pair<Int, Int>>()
        for (apt in sorted) {
            val start = minutesFrom(apt.startTime)
            val end = minutesFrom(apt.endTime)
            if (start > cursor) gaps += cursor to start
            cursor = maxOf(cursor, end)
        }
        if (cursor < totalMinutes) gaps += cursor to totalMinutes

        gaps.forEach { (start, end) ->
            if (end - start < 20) return@forEach // ignora huecos minúsculos por solapes
            val y = hourHeight * (start / 60f)
            val h = hourHeight * ((end - start) / 60f)
            AvailableSlot(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = contentStartX, y = y + 4.dp)
                    .width(cardWidth)
                    .height(h - 8.dp),
                onClick = { onAvailableClick(LocalTime(startHour + start / 60, start % 60)) }
            )
        }

        sorted.forEach { apt ->
            val start = minutesFrom(apt.startTime)
            val end = minutesFrom(apt.endTime)
            val y = hourHeight * (start / 60f)
            val h = hourHeight * ((end - start) / 60f)
            AppointmentCard(
                appointment = apt,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = contentStartX, y = y + 4.dp)
                    .width(cardWidth)
                    .height(h - 8.dp),
                onClick = { onAppointmentClick(apt) }
            )
        }
    }
}

private fun formatHour(hour: Int): String {
    val h12 = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }
    return "$h12 ${if (hour < 12) "AM" else "PM"}"
}