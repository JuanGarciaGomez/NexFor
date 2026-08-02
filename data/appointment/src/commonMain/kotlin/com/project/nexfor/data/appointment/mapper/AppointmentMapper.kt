package com.project.nexfor.data.appointment.mapper

import com.project.nexfor.data.appointment.dto.AppointmentDto
import com.project.nexfor.domain.appointment.model.Appointment

fun AppointmentDto.toDomain(): Appointment {
    return Appointment(
        id = id,
        appointmentDate = appointmentDate,
        startTime = startTime,
        endTime = endTime,
        status = status,
        customerName = "${customer.firstName} ${customer.lastName}",
        services = services
    )
}
