package com.project.nexfor.di

import com.project.nexfor.core.network.client.SessionManager
import com.project.nexfor.core.network.di.networkModule
import com.project.nexfor.data.appointment.di.dataAppointmentModule
import com.project.nexfor.data.login.di.loginDataModule
import com.project.nexfor.feature.appointment.di.featureAppointmentModule
import com.project.nexfor.feature.auth.di.loginModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/**
 * Common DI module for shared dependencies.
 */
val appModule = module {
    // Shared dependencies will go here
}

/**
 * Initializes Koin for the application.
 *
 * @param appDeclaration Optional Koin configuration (e.g., for Android application context).
 */
fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            appModule,
            networkModule,
            loginModule,
            loginDataModule,
            dataAppointmentModule,
            featureAppointmentModule,
        )
    }.also {
        it.koin.get<SessionManager>().restoreSession()
    }
