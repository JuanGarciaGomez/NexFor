package com.project.nexfor.data.appointment.repository

import com.project.nexfor.data.appointment.mapper.toDomain
import com.project.nexfor.data.appointment.mapper.toDto
import com.project.nexfor.data.appointment.remote.AppointmentApiService
import com.project.nexfor.domain.appointment.exception.UnauthorizedException
import com.project.nexfor.domain.appointment.model.Appointment
import com.project.nexfor.domain.appointment.model.CreateAppointmentRequest
import com.project.nexfor.domain.appointment.repository.AppointmentRepository
import io.ktor.http.isSuccess

class AppointmentRepositoryImpl(
    private val apiService: AppointmentApiService
) : AppointmentRepository {
    override suspend fun getAppointments(): List<Appointment> {
        return apiService.getAppointments().data?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun createAppointment(request: CreateAppointmentRequest): Result<Unit> {
        return runCatching {
            val response = apiService.createAppointment(request.toDto())
            if (response.status.value == 401) {
                throw UnauthorizedException("Session expired")
            }
            if (!response.status.isSuccess()) {
                throw Exception("Error creating appointment: ${response.status.value}")
            }
        }
    }
}
