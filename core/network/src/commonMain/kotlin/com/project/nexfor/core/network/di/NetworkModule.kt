package com.project.nexfor.core.network.di

import com.project.nexfor.core.network.client.AuthInterceptor
import com.project.nexfor.core.network.client.HttpClientFactory
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
}
