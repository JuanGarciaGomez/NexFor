package com.project.nexfor.core.network.client

class SessionManager(
    private val tokenStorage: TokenStorage,
    private val authInterceptor: AuthInterceptor
) {
    /** Llamar una vez al iniciar la app, para restaurar la sesión si existe. */
    fun restoreSession() {
        val savedToken = tokenStorage.getAccessToken()
        authInterceptor.updateToken(savedToken)
    }

    /** Llamar justo después de un login o refresh exitoso. */
    fun onLoginSuccess(accessToken: String, refreshToken: String) {
        tokenStorage.saveTokens(accessToken, refreshToken)
        authInterceptor.updateToken(accessToken)
    }

    fun getRefreshToken(): String? = tokenStorage.getRefreshToken()

    /** Llamar en logout. */
    fun clearSession() {
        tokenStorage.clearTokens()
        authInterceptor.updateToken(null)
    }

    fun isLoggedIn(): Boolean = tokenStorage.getAccessToken() != null
}