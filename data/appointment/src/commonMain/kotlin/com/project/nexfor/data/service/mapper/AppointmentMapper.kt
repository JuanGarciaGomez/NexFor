package com.project.nexfor.data.service.mapper

import com.project.nexfor.data.service.dto.AppointmentDto
import com.project.nexfor.data.service.dto.AppointmentServiceRequestDto
import com.project.nexfor.data.service.dto.CreateAppointmentRequestDto
import com.project.nexfor.domain.appointment.model.Appointment
import com.project.nexfor.domain.appointment.model.CreateAppointmentRequest

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

fun CreateAppointmentRequest.toDto(): CreateAppointmentRequestDto {
    return CreateAppointmentRequestDto(
        customerId = customerId,
        appointmentDate = appointmentDate,
        startTime = startTime,
        endTime = endTime,
        notes = notes,
        appointmentServices = appointmentServices.map {
            AppointmentServiceRequestDto(
                serviceId = it.serviceId,
                employeeId = it.employeeId
            )
        }
    )
}
