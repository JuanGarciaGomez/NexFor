package com.project.nexfor.feature.appointment.ui

import androidx.lifecycle.viewModelScope
import com.project.nexfor.core.ui.mvi.BaseViewModel
import com.project.nexfor.domain.appointment.usecase.GetAppointmentsUseCase
import com.project.nexfor.feature.appointment.model.toDayUi
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class AppointmentViewModel(
    private val getAppointmentsUseCase: GetAppointmentsUseCase
) : BaseViewModel<AppointmentState, AppointmentIntent, AppointmentEffect>(
    initialState = AppointmentState()
) {

    @OptIn(ExperimentalTime::class)
    private val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

    init {
        selectDate(today)
    }

    override fun onIntent(intent: AppointmentIntent) {
        when (intent) {
            AppointmentIntent.LoadAppointments -> loadAppointments()
            is AppointmentIntent.OnAppointmentClick -> {}
            is AppointmentIntent.OnDateSelected -> selectDate(intent.date)
        }
    }

    private fun selectDate(date: LocalDate) {
        val visibleDays = (-2..4).map { offset -> date.plus(offset, DateTimeUnit.DAY).toDayUi() }
        setState { copy(selectedDate = date, visibleDays = visibleDays) }
    }

    private fun loadAppointments() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            runCatching { getAppointmentsUseCase() }
                .onSuccess { result ->
                    setState { copy(isLoading = false, appointments = result) }
                }
                .onFailure { throwable ->
                    setState { copy(isLoading = false) }
                    sendEffect {
                        AppointmentEffect.ShowError(
                            throwable.message ?: "Error al cargar citas"
                        )
                    }
                }
        }
    }
}