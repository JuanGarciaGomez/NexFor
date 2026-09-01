package com.project.nexfor.domain.service.usecase

import com.project.nexfor.domain.service.model.Service
import com.project.nexfor.domain.service.repository.ServiceRepository

class GetServicesUseCase(
    private val repository: ServiceRepository
) {
    suspend operator fun invoke(): List<Service> {
        return repository.getServices()
    }
}
