package com.project.nexfor.data.customer.di

import com.project.nexfor.data.customer.remote.CustomerApiService
import com.project.nexfor.data.customer.repository.CustomerRepositoryImpl
import com.project.nexfor.domain.customer.repository.CustomerRepository
import com.project.nexfor.domain.customer.usecase.GetCustomersUseCase
import org.koin.dsl.module

val dataCustomerModule = module {
    single { CustomerApiService(get()) }
    single<CustomerRepository> { CustomerRepositoryImpl(get()) }
    factory { GetCustomersUseCase(get()) }
}
