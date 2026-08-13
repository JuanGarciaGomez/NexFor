package com.project.nexfor.data.login.remote

import com.project.nexfor.core.network.dto.ApiResponseDto
import com.project.nexfor.core.network.model.NetworkResult
import com.project.nexfor.core.network.util.toNetworkResult
import com.project.nexfor.data.login.dto.LoginDataDto
import com.project.nexfor.data.login.dto.LoginRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class LoginApiService(
    private val httpClient: HttpClient
) {
    suspend fun postLogin(email: String, password: String): NetworkResult<LoginDataDto> {
        return httpClient.post("auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequestDto(email = email, password = password))
        }.body<ApiResponseDto<LoginDataDto>>().toNetworkResult()
    }
}