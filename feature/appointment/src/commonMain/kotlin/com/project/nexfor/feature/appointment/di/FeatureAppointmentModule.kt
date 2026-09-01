package com.project.nexfor.feature.appointment.di

import com.project.nexfor.domain.appointment.usecase.CreateAppointmentUseCase
import com.project.nexfor.domain.customer.usecase.GetCustomersUseCase
import com.project.nexfor.domain.service.usecase.GetServicesUseCase
import com.project.nexfor.feature.appointment.ui.AppointmentViewModel
import com.project.nexfor.feature.appointment.ui.create.CreateAppointmentViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureAppointmentModule = module {
    factory { CreateAppointmentUseCase(get()) }
    factory { GetCustomersUseCase(get()) }
    factory { GetServicesUseCase(get()) }
    viewModelOf(::AppointmentViewModel)
    viewModelOf(::CreateAppointmentViewModel)
}
