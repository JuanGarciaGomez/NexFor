package com.project.nexfor.domain.appointment.repository

import com.project.nexfor.domain.appointment.model.Appointment

interface AppointmentRepository {
    suspend fun getAppointments(): List<Appointment>
}
