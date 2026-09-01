package com.project.nexfor.domain.service.repository

import com.project.nexfor.domain.service.model.Service

interface ServiceRepository {
    suspend fun getServices(): List<Service>
}
