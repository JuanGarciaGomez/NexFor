package com.project.nexfor.feature.appointment.ui.create

import com.project.nexfor.core.ui.mvi.UiEffect
import com.project.nexfor.core.ui.mvi.UiIntent
import com.project.nexfor.core.ui.mvi.UiState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class CustomerUi(val id: String, val name: String)
data class ServiceUi(val id: String, val name: String, val employeeId: String)

data class CreateAppointmentState(
    val isLoading: Boolean = false,
    val customers: List<CustomerUi> = emptyList(),
    val availableServices: List<ServiceUi> = emptyList(),
    val selectedCustomerId: String = "",
    val appointmentDate: LocalDate? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val notes: String = "",
    val selectedServiceIds: List<String> = emptyList(),
    val error: String? = null,
    val isSuccess: Boolean = false
) : UiState

sealed interface CreateAppointmentIntent : UiIntent {
    data object LoadInitialData : CreateAppointmentIntent
    data class OnCustomerSelected(val id: String) : CreateAppointmentIntent
    data class OnDateSelected(val date: LocalDate) : CreateAppointmentIntent
    data class OnStartTimeSelected(val time: LocalTime) : CreateAppointmentIntent
    data class OnEndTimeSelected(val time: LocalTime) : CreateAppointmentIntent
    data class OnServiceToggled(val serviceId: String) : CreateAppointmentIntent
    data class OnNotesChanged(val notes: String) : CreateAppointmentIntent
    data object SaveAppointment : CreateAppointmentIntent
}

sealed interface CreateAppointmentEffect : UiEffect {
    data class ShowError(val message: String) : CreateAppointmentEffect
    data object NavigateBack : CreateAppointmentEffect
}
