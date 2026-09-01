package com.project.nexfor.domain.appointment.model

data class CreateAppointmentRequest(
    val customerId: String,
    val appointmentDate: String,
    val startTime: String,
    val endTime: String,
    val notes: String,
    val appointmentServices: List<AppointmentServiceRequest>
)

data class AppointmentServiceRequest(
    val serviceId: String,
    val employeeId: String?
)
