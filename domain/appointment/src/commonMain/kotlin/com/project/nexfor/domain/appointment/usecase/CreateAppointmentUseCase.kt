package com.project.nexfor.domain.appointment.usecase

import com.project.nexfor.domain.appointment.model.CreateAppointmentRequest
import com.project.nexfor.domain.appointment.repository.AppointmentRepository

class CreateAppointmentUseCase(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(request: CreateAppointmentRequest): Result<Unit> {
        return repository.createAppointment(request)
    }
}
