package com.project.nexfor.data.service.di

import com.project.nexfor.data.service.remote.AppointmentApiService
import com.project.nexfor.data.service.repository.AppointmentRepositoryImpl
import com.project.nexfor.domain.appointment.repository.AppointmentRepository
import com.project.nexfor.domain.appointment.usecase.GetAppointmentsUseCase
import org.koin.dsl.module

val dataAppointmentModule = module {
    single { AppointmentApiService(get()) }
    single<AppointmentRepository> { AppointmentRepositoryImpl(get()) }
    factory { GetAppointmentsUseCase(get()) }
}
