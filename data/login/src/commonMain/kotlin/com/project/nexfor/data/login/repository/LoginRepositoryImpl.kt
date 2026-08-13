package com.project.nexfor.data.login.repository

import com.project.nexfor.data.login.mapper.toDomain
import com.project.nexfor.data.login.remote.LoginApiService
import com.project.nexfor.domain.login.model.Login
import com.project.nexfor.domain.login.repository.LoginRepository

class LoginRepositoryImpl(
    private val loginApiService: LoginApiService
) : LoginRepository {
    override suspend fun getLogin(): Login {
        val response = loginApiService.getLogin()
        if (!response.success || response.data == null) {
            throw IllegalStateException(response.message)
        }
        return response.data.toDomain()
    }
}
