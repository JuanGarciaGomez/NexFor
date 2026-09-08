package com.project.nexfor.domain.customer.usecase

import com.project.nexfor.domain.customer.model.Customer
import com.project.nexfor.domain.customer.repository.CustomerRepository

class GetCustomersUseCase(
    private val repository: CustomerRepository
) {
    suspend operator fun invoke(): List<Customer> {
        return repository.getCustomers()
    }
}
