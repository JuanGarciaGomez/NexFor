package com.project.nexfor.domain.customer.model

data class Customer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String?,
    val phone: String?,
    val isActive: Boolean
) {
    val fullName: String get() = "$firstName $lastName"
}