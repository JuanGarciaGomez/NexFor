package com.project.nexfor.data.appointment.remote

import com.project.nexfor.data.appointment.dto.AppointmentResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class AppointmentApiService(
    private val httpClient: HttpClient
) {
    suspend fun getAppointments(): AppointmentResponseDto {
        return httpClient.get("appointments").body()
    }
}
