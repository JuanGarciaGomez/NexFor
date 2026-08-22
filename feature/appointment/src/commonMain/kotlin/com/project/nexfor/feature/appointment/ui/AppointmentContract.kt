package com.project.nexfor.feature.appointment.ui

import com.project.nexfor.core.ui.mvi.UiEffect
import com.project.nexfor.core.ui.mvi.UiIntent
import com.project.nexfor.core.ui.mvi.UiState
import com.project.nexfor.domain.appointment.model.Appointment
import com.project.nexfor.feature.appointment.model.DayUi
import kotlinx.datetime.LocalDate

data class AppointmentState(
    val isLoading: Boolean = false,
    val appointments: List<Appointment> = emptyList(),
    val selectedDate: LocalDate = DEFAULT_DATE,
    val visibleDays: List<DayUi> = emptyList(),
    val error: String? = null
) : UiState


private val DEFAULT_DATE = LocalDate(2000, 1, 1)

sealed interface AppointmentIntent : UiIntent {
    data object LoadAppointments : AppointmentIntent
    data class OnAppointmentClick(val appointment: Appointment) : AppointmentIntent
    data class OnDateSelected(val date: LocalDate) : AppointmentIntent
}

sealed interface AppointmentEffect : UiEffect {
    data class ShowError(val message: String) : AppointmentEffect
    data class NavigateToDetail(val id: String) : AppointmentEffect
}