package com.project.nexfor.feature.appointment.ui.create

import androidx.lifecycle.viewModelScope
import com.project.nexfor.core.ui.mvi.BaseViewModel
import com.project.nexfor.domain.appointment.exception.UnauthorizedException
import com.project.nexfor.domain.appointment.model.AppointmentServiceRequest
import com.project.nexfor.domain.appointment.model.CreateAppointmentRequest
import com.project.nexfor.domain.appointment.usecase.CreateAppointmentUseCase
import com.project.nexfor.domain.customer.usecase.GetCustomersUseCase
import com.project.nexfor.domain.service.usecase.GetServicesUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CreateAppointmentViewModel(
    private val createAppointmentUseCase: CreateAppointmentUseCase,
    private val getCustomersUseCase: GetCustomersUseCase,
    private val getServicesUseCase: GetServicesUseCase
) : BaseViewModel<CreateAppointmentState, CreateAppointmentIntent, CreateAppointmentEffect>(
    initialState = CreateAppointmentState()
) {

    init {
        onIntent(CreateAppointmentIntent.LoadInitialData)
    }

    override fun onIntent(intent: CreateAppointmentIntent) {
        when (intent) {
            CreateAppointmentIntent.LoadInitialData -> loadInitialData()
            is CreateAppointmentIntent.OnCustomerSelected -> setState { copy(selectedCustomerId = intent.id) }
            is CreateAppointmentIntent.OnDateSelected -> setState { copy(appointmentDate = intent.date) }
            is CreateAppointmentIntent.OnStartTimeSelected -> setState { copy(startTime = intent.time) }
            is CreateAppointmentIntent.OnEndTimeSelected -> setState { copy(endTime = intent.time) }
            is CreateAppointmentIntent.OnNotesChanged -> setState { copy(notes = intent.notes) }
            is CreateAppointmentIntent.OnServiceToggled -> toggleService(intent.serviceId)
            CreateAppointmentIntent.SaveAppointment -> saveAppointment()
        }
    }

    private fun toggleService(serviceId: String) {
        val current = state.value.selectedServiceIds
        val next = if (current.contains(serviceId)) {
            current - serviceId
        } else {
            current + serviceId
        }
        setState { copy(selectedServiceIds = next) }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            runCatching {
                val customersDeferred = async { getCustomersUseCase() }
                val servicesDeferred = async { getServicesUseCase() }

                val customers = customersDeferred.await()
                val services = servicesDeferred.await()

                Pair(customers, services)
            }.onSuccess { (customers, services) ->
                setState {
                    copy(
                        isLoading = false,
                        customers = customers.map { CustomerUi(it.id, it.fullName) },
                        availableServices = services.map {
                            ServiceUi(
                                it.id,
                                it.name,
                                it.employeeId ?: ""
                            )
                        },
                        selectedCustomerId = customers.firstOrNull()?.id ?: ""
                    )
                }
            }.onFailure { throwable ->
                setState { copy(isLoading = false, error = throwable.message) }
            }
        }
    }

    private fun saveAppointment() {
        val currentState = state.value
        val date = currentState.appointmentDate
        val startTime = currentState.startTime
        val endTime = currentState.endTime

        if (date == null || startTime == null || endTime == null) {
            sendEffect { CreateAppointmentEffect.ShowError("Por favor completa la fecha y hora") }
            return
        }

        if (currentState.selectedServiceIds.isEmpty()) {
            sendEffect { CreateAppointmentEffect.ShowError("Por favor selecciona al menos un servicio") }
            return
        }

        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }

            // Format date to YYYY-MM-DD as most backends expect when sending separate time fields
            val isoDate = date.toString()

            val appointmentServices = currentState.selectedServiceIds.map { serviceId ->
                val service = currentState.availableServices.find { it.id == serviceId }
                val employeeId = service?.employeeId.takeIf { it?.isNotEmpty() == true }
                AppointmentServiceRequest(
                    serviceId = serviceId,
                    employeeId = employeeId
                )
            }

            val request = CreateAppointmentRequest(
                customerId = currentState.selectedCustomerId,
                appointmentDate = isoDate,
                startTime = "${
                    startTime.hour.toString().padStart(2, '0')
                }:${startTime.minute.toString().padStart(2, '0')}",
                endTime = "${endTime.hour.toString().padStart(2, '0')}:${
                    endTime.minute.toString().padStart(2, '0')
                }",
                notes = currentState.notes,
                appointmentServices = appointmentServices
            )

            createAppointmentUseCase(request)
                .onSuccess {
                    setState { copy(isLoading = false, isSuccess = true) }
                    sendEffect { CreateAppointmentEffect.NavigateBack }
                }
                .onFailure { throwable ->
                    setState { copy(isLoading = false) }
                    if (throwable is UnauthorizedException) {
                         sendEffect { CreateAppointmentEffect.ShowError("Sesión expirada. Por favor, inicia sesión de nuevo.") }
                    } else {
                        sendEffect {
                            CreateAppointmentEffect.ShowError(
                                throwable.message ?: "Error al crear la cita"
                            )
                        }
                    }
                }
        }
    }
}
