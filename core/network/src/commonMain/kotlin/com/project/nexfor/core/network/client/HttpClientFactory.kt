package com.project.nexfor.core.network.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Factory class to build and configure the Ktor HttpClient.
 */
class HttpClientFactory(
    private val authInterceptor: AuthInterceptor
) {
    /**
     * Creates and configures a new instance of [HttpClient].
     *
     * @param isDebug Whether the app is running in debug mode (affects logging).
     * @return A configured [HttpClient] instance.
     */
    fun create(isDebug: Boolean): HttpClient {
        return HttpClient {
            // JSON Serialization configuration
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }

            // Timeout configuration
            install(HttpTimeout) {
                connectTimeoutMillis = 15_000
                requestTimeoutMillis = 30_000
            }

            // Logging configuration
            install(Logging) {
                level = if (isDebug) LogLevel.HEADERS else LogLevel.NONE
            }

            // Authentication interceptor
            install(authInterceptor.plugin)

            // Default headers
            install(DefaultRequest) {
                contentType(ContentType.Application.Json)
            }
        }
    }
}
