package com.project.nexfor.feature.appointment

import androidx.lifecycle.viewModelScope
import com.project.nexfor.core.ui.mvi.BaseViewModel
import com.project.nexfor.domain.appointment.usecase.GetAppointmentsUseCase
import kotlinx.coroutines.launch

class AppointmentViewModel(
    private val getAppointmentsUseCase: GetAppointmentsUseCase
) : BaseViewModel<AppointmentContract.State, AppointmentContract.Intent, AppointmentContract.Effect>(
    initialState = AppointmentContract.State()
) {

    override fun onIntent(intent: AppointmentContract.Intent) {
        when (intent) {
            is AppointmentContract.Intent.LoadAppointments -> loadAppointments()
            is AppointmentContract.Intent.OnAppointmentClick -> {
                sendEffect { AppointmentContract.Effect.NavigateToDetail(intent.appointment.id) }
            }
        }
    }

    private fun loadAppointments() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            try {
                val result = getAppointmentsUseCase()
                setState { copy(isLoading = false, appointments = result) }
            } catch (e: Exception) {
                setState { copy(isLoading = false, error = e.message ?: "Unknown error") }
                sendEffect { AppointmentContract.Effect.ShowError(e.message ?: "Unknown error") }
            }
        }
    }
}
