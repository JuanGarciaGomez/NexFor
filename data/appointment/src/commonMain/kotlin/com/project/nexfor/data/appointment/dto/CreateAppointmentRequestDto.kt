package com.project.nexfor.data.appointment.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateAppointmentRequestDto(
    val customerId: String,
    val appointmentDate: String,
    val startTime: String,
    val endTime: String,
    val notes: String,
    val appointmentServices: List<AppointmentServiceRequestDto>
)

@Serializable
data class AppointmentServiceRequestDto(
    val serviceId: String,
    val employeeId: String? = null
)
