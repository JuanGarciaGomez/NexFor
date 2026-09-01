package com.project.nexfor.domain.service.model

data class Service(
    val id: String,
    val name: String,
    val description: String?,
    val price: Double,
    val durationMinutes: Int,
    val employeeId: String?
)