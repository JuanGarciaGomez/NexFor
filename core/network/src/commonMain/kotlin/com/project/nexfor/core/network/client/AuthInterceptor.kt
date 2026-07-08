package com.project.nexfor.core.network.client

import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.http.HttpHeaders

/**
 * A Ktor client plugin that injects the Authorization header into every request.
 */
class AuthInterceptor {
    private var token: String? = null

    /**
     * Updates the current authentication token.
     * This should be called by the auth module after a successful login or token refresh.
     *
     * @param newToken The new JWT or Bearer token, or null to clear it.
     */
    fun updateToken(newToken: String?) {
        token = newToken
    }

    /**
     * The actual Ktor plugin implementation.
     */
    val plugin = createClientPlugin("AuthInterceptor") {
        onRequest { request, _ ->
            token?.let {
                request.headers.append(HttpHeaders.Authorization, "Bearer $it")
            }
        }
    }
}
