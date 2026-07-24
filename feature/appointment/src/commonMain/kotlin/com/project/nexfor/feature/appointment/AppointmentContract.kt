package com.project.nexfor.feature.appointment

import com.project.nexfor.core.ui.mvi.UiEffect
import com.project.nexfor.core.ui.mvi.UiIntent
import com.project.nexfor.core.ui.mvi.UiState
import com.project.nexfor.domain.appointment.model.Appointment

interface AppointmentContract {
    data class State(
        val isLoading: Boolean = false,
        val appointments: List<Appointment> = emptyList(),
        val error: String? = null
    ) : UiState

    sealed interface Intent : UiIntent {
        data object LoadAppointments : Intent
        data class OnAppointmentClick(val appointment: Appointment) : Intent
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val message: String) : Effect
        data class NavigateToDetail(val id: String) : Effect
    }
}
