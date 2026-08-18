package com.project.nexfor.core.network.di

import com.project.nexfor.core.network.client.AuthInterceptor
import com.project.nexfor.core.network.client.HttpClientFactory
import com.project.nexfor.core.network.client.SessionManager
import com.project.nexfor.core.network.client.TokenStorage
import com.project.nexfor.core.network.client.TokenStorageImpl
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import org.koin.dsl.module

/**
 * Koin module for network-related dependencies.
 */
val networkModule = module {
    /**
     * Singleton for [AuthInterceptor].
     */
    single { AuthInterceptor() }

    /**
     * Singleton for [HttpClientFactory].
     */
    single { HttpClientFactory(get()) }

    /**
     * Provides the configured [HttpClient].
     * Note: In a real app, 'isDebug' would be provided by a BuildKonfig or similar.
     * Here we default to true for development.
     */
    single<HttpClient> { get<HttpClientFactory>().create(isDebug = true) }

    single<Settings> { Settings() }
    single<TokenStorage> { TokenStorageImpl(get()) }
    single { SessionManager(get(), get()) }
}
