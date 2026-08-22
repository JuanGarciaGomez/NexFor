package com.project.nexfor.feature.appointment.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.project.nexfor.core.ui.theme.NexForTheme
import com.project.nexfor.domain.appointment.model.Appointment
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime


enum class AppointmentStatusUi { CONFIRMED, PENDING, CANCELLED, COMPLETED }

data class AppointmentUi(
    val id: String,
    val clientName: String,
    val service: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val status: AppointmentStatusUi,
)


fun Appointment.toUi(): AppointmentUi = AppointmentUi(
    id = id,
    clientName = customerName,
    service = services.joinToString(" + "),
    date = LocalDate.parse(appointmentDate),
    startTime = LocalTime.parse(startTime),
    endTime = LocalTime.parse(endTime),
    status = status.toStatusUi()
)

private fun String.toStatusUi(): AppointmentStatusUi = when (uppercase()) {
    "CONFIRMED" -> AppointmentStatusUi.CONFIRMED
    "PENDING" -> AppointmentStatusUi.PENDING
    "CANCELLED", "CANCELED" -> AppointmentStatusUi.CANCELLED
    "COMPLETED" -> AppointmentStatusUi.COMPLETED
    else -> AppointmentStatusUi.PENDING
}

@Composable
fun AppointmentStatusUi.accentColor(): Color = when (this) {
    AppointmentStatusUi.CONFIRMED -> NexForTheme.extra.teal
    AppointmentStatusUi.PENDING -> MaterialTheme.colorScheme.primary
    AppointmentStatusUi.CANCELLED -> MaterialTheme.colorScheme.error
    AppointmentStatusUi.COMPLETED -> NexForTheme.extra.mutedForeground
}