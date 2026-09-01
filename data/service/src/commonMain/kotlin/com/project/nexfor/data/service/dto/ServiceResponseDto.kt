package com.project.nexfor.data.service.dto

import kotlinx.serialization.Serializable

@Serializable
data class ServiceResponseDto(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val data: ServiceDataDto? = null
)

@Serializable
data class ServiceDataDto(
    val items: List<ServiceDetailDto>,
    val pagination: PaginationDto? = null
)

@Serializable
data class ServiceDetailDto(
    val id: String,
    val name: String,
    val description: String? = null,
    val price: Double,
    val durationMinutes: Int,
    val employeeId: String? = null
)

@Serializable
data class PaginationDto(
    val total: Int,
    val limit: Int,
    val offset: Int,
    val currentPage: Int,
    val totalPages: Int,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean
)
