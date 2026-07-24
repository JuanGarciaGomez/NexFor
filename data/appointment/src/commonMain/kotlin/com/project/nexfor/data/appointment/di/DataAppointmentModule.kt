package com.project.nexfor.data.appointment.di

import com.project.nexfor.data.appointment.repository.AppointmentRepositoryImpl
import com.project.nexfor.domain.appointment.repository.AppointmentRepository
import com.project.nexfor.domain.appointment.usecase.GetAppointmentsUseCase
import org.koin.dsl.module

val dataAppointmentModule = module {
    single<AppointmentRepository> { AppointmentRepositoryImpl() }
    factory { GetAppointmentsUseCase(get()) }
}
