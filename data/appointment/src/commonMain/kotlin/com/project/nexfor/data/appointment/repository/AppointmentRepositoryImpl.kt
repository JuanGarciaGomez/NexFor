package com.project.nexfor.data.appointment.repository

import com.project.nexfor.data.appointment.mapper.toDomain
import com.project.nexfor.data.appointment.remote.AppointmentApiService
import com.project.nexfor.domain.appointment.model.Appointment
import com.project.nexfor.domain.appointment.repository.AppointmentRepository

class AppointmentRepositoryImpl(
    private val apiService: AppointmentApiService
) : AppointmentRepository {
    override suspend fun getAppointments(): List<Appointment> {
        return apiService.getAppointments().data.map { it.toDomain() }
    }
}
