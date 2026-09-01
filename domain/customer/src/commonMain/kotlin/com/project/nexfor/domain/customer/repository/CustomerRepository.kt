package com.project.nexfor.domain.customer.repository

import com.project.nexfor.domain.customer.model.Customer

interface CustomerRepository {
    suspend fun getCustomers(): List<Customer>
}
