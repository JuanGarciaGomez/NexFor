package com.project.nexfor.data.customer.repository

import com.project.nexfor.data.customer.mapper.toDomain
import com.project.nexfor.data.customer.remote.CustomerApiService
import com.project.nexfor.domain.customer.model.Customer
import com.project.nexfor.domain.customer.repository.CustomerRepository

class CustomerRepositoryImpl(
    private val apiService: CustomerApiService
) : CustomerRepository {
    override suspend fun getCustomers(): List<Customer> {
        return apiService.getCustomers().data?.items?.map { it.toDomain() } ?: emptyList()
    }
}
