package com.project.nexfor.data.login.remote

import com.project.nexfor.data.login.dto.LoginResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class LoginApiService(
    private val httpClient: HttpClient
) {
    suspend fun getLogin(): LoginResponseDto{
        return httpClient.get("auth/login").body()
    }
}