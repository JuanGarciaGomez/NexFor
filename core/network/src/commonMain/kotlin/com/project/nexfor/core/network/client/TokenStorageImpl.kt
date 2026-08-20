package com.project.nexfor.core.network.client

import com.russhwolf.settings.Settings

class TokenStorageImpl(
    private val settings: Settings
) : TokenStorage {
    override fun saveTokens(accessToken: String, refreshToken: String) {
        settings.putString(KEY_ACCESS_TOKEN, accessToken)
        settings.putString(KEY_REFRESH_TOKEN, refreshToken)
    }

    override fun getAccessToken(): String? =
        settings.getStringOrNull(KEY_ACCESS_TOKEN)

    override fun getRefreshToken(): String? =
        settings.getStringOrNull(KEY_REFRESH_TOKEN)

    override fun clearTokens() {
        settings.remove(KEY_ACCESS_TOKEN)
        settings.remove(KEY_REFRESH_TOKEN)
    }

    private companion object {
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}