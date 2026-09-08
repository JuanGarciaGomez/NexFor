package com.project.nexfor.data.customer.remote

import com.project.nexfor.data.customer.dto.CustomerResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CustomerApiService(
    private val httpClient: HttpClient
) {
    suspend fun getCustomers(): CustomerResponseDto {
        return httpClient.get("customers").body()
    }
}
