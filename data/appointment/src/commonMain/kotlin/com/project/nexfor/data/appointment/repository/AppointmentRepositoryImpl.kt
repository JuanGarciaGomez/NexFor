package com.project.nexfor.data.appointment.repository

import com.project.nexfor.domain.appointment.model.Appointment
import com.project.nexfor.domain.appointment.repository.AppointmentRepository

class AppointmentRepositoryImpl : AppointmentRepository {
    override suspend fun getAppointments(): List<Appointment> {
        // Mock data for now
        return listOf(
            Appointment("1", "Dentist", "Routine checkup", "2023-10-25", "Scheduled"),
            Appointment("2", "Business Meeting", "Discuss project NexFor", "2023-10-26", "Scheduled")
        )
    }
}
