package com.project.nexfor.data.service.mapper

import com.project.nexfor.data.service.dto.ServiceDetailDto
import com.project.nexfor.domain.service.model.Service

fun ServiceDetailDto.toDomain(): Service {
    return Service(
        id = id,
        name = name,
        description = description,
        price = price,
        durationMinutes = durationMinutes,
        employeeId = employeeId
    )
}
