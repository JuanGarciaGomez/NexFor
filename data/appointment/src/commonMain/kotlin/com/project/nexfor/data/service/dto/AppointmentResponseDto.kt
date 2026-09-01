package com.project.nexfor.data.service.dto

import kotlinx.serialization.Serializable

@Serializable
data class AppointmentResponseDto(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val data: List<AppointmentDto>? = null
)

@Serializable
data class AppointmentDto(
    val id: String,
    val appointmentDate: String,
    val startTime: String,
    val endTime: String,
    val status: String,
    val customer: CustomerDto,
    val services: List<String>
)

@Serializable
data class CustomerDto(
    val firstName: String,
    val lastName: String
)
