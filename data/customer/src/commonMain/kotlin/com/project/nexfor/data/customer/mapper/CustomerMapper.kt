package com.project.nexfor.data.customer.mapper

import com.project.nexfor.data.customer.dto.CustomerDetailDto
import com.project.nexfor.domain.customer.model.Customer

fun CustomerDetailDto.toDomain(): Customer {
    return Customer(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone,
        isActive = isActive
    )
}
