package com.project.nexfor.feature.appointment.di

import com.project.nexfor.feature.appointment.ui.AppointmentViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featureAppointmentModule = module {
    viewModelOf(::AppointmentViewModel)
}
