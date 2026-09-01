package com.project.nexfor.domain.appointment.repository

import com.project.nexfor.domain.appointment.model.Appointment
import com.project.nexfor.domain.appointment.model.CreateAppointmentRequest

interface AppointmentRepository {
    suspend fun getAppointments(): List<Appointment>
    suspend fun createAppointment(request: CreateAppointmentRequest): Result<Unit>
}
