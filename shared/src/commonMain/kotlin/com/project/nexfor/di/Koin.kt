package com.project.nexfor.di

import com.project.nexfor.core.network.di.networkModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
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
            networkModule
        )
    }
