package com.project.nexfor.domain.appointment.usecase

import com.project.nexfor.domain.appointment.model.Appointment
import com.project.nexfor.domain.appointment.repository.AppointmentRepository

class GetAppointmentsUseCase(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(): List<Appointment> {
        return repository.getAppointments()
    }
}
