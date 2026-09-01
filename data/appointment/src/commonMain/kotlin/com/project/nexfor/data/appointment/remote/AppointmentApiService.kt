package com.project.nexfor.data.appointment.remote

import com.project.nexfor.data.appointment.dto.AppointmentResponseDto
import com.project.nexfor.data.appointment.dto.CreateAppointmentRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AppointmentApiService(
    private val httpClient: HttpClient
) {
    suspend fun getAppointments(): AppointmentResponseDto {
        return httpClient.get("appointments").body()
    }

    suspend fun createAppointment(request: CreateAppointmentRequestDto): HttpResponse {
        val response = httpClient.post("appointments") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response
    }
}
