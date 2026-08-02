package com.project.nexfor.domain.appointment.model

data class Appointment(
    val id: String,
    val appointmentDate: String,
    val startTime: String,
    val endTime: String,
    val status: String,
    val customerName: String,
    val services: List<String>,
)
