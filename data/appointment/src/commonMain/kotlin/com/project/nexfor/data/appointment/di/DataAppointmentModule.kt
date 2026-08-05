package com.project.nexfor.data.appointment.di

import com.project.nexfor.data.appointment.remote.AppointmentApiService
import com.project.nexfor.data.appointment.repository.AppointmentRepositoryImpl
import com.project.nexfor.domain.appointment.repository.AppointmentRepository
import com.project.nexfor.domain.appointment.usecase.GetAppointmentsUseCase
import org.koin.dsl.module

val dataAppointmentModule = module {
    single { AppointmentApiService(get()) }
    single<AppointmentRepository> { AppointmentRepositoryImpl(get()) }
    factory { GetAppointmentsUseCase(get()) }
}
