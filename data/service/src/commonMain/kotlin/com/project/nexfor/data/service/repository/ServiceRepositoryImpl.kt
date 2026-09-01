package com.project.nexfor.data.service.repository

import com.project.nexfor.data.service.mapper.toDomain
import com.project.nexfor.data.service.remote.ServiceApiService
import com.project.nexfor.domain.service.model.Service
import com.project.nexfor.domain.service.repository.ServiceRepository

class ServiceRepositoryImpl(
    private val apiService: ServiceApiService
) : ServiceRepository {
    override suspend fun getServices(): List<Service> {
        return apiService.getServices().data?.items?.map { it.toDomain() } ?: emptyList()
    }
}
