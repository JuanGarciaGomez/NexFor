package com.project.nexfor.data.customer.dto

import kotlinx.serialization.Serializable

@Serializable
data class CustomerResponseDto(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val data: CustomerDataDto? = null
)

@Serializable
data class CustomerDataDto(
    val items: List<CustomerDetailDto>,
    val pagination: PaginationDto? = null
)

@Serializable
data class CustomerDetailDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String? = null,
    val phone: String? = null,
    val isActive: Boolean = true
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
