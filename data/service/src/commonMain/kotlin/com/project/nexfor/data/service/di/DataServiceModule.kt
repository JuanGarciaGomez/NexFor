package com.project.nexfor.data.service.di

import com.project.nexfor.data.service.remote.ServiceApiService
import com.project.nexfor.data.service.repository.ServiceRepositoryImpl
import com.project.nexfor.domain.service.repository.ServiceRepository
import com.project.nexfor.domain.service.usecase.GetServicesUseCase
import org.koin.dsl.module

val dataServiceModule = module {
    single { ServiceApiService(get()) }
    single<ServiceRepository> { ServiceRepositoryImpl(get()) }
    factory { GetServicesUseCase(get()) }
}
