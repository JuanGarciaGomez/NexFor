package com.project.nexfor.feature.appointment.model

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

data class DayUi(
    val date: LocalDate,
    val dayLabel: String,
    val dayNumber: Int
)


fun LocalDate.toDayUi(): DayUi = DayUi(
    date = this,
    dayLabel = dayOfWeek.toShortLabel(),
    dayNumber = dayOfMonth
)

private fun DayOfWeek.toShortLabel(): String = when (this) {
    DayOfWeek.MONDAY -> "Mon"
    DayOfWeek.TUESDAY -> "Tue"
    DayOfWeek.WEDNESDAY -> "Wed"
    DayOfWeek.THURSDAY -> "Thu"
    DayOfWeek.FRIDAY -> "Fri"
    DayOfWeek.SATURDAY -> "Sat"
    DayOfWeek.SUNDAY -> "Sun"
}