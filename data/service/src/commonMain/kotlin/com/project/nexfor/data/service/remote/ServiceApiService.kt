package com.project.nexfor.data.service.remote

import com.project.nexfor.data.service.dto.ServiceResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ServiceApiService(
    private val httpClient: HttpClient
) {
    suspend fun getServices(): ServiceResponseDto {
        return httpClient.get("services").body()
    }
}