package com.project.nexfor.data.login.repository

import com.project.nexfor.core.network.client.SessionManager
import com.project.nexfor.core.network.model.NetworkError
import com.project.nexfor.core.network.model.NetworkResult
import com.project.nexfor.data.login.mapper.toDomain
import com.project.nexfor.data.login.remote.LoginApiService
import com.project.nexfor.domain.login.model.Login
import com.project.nexfor.domain.login.repository.LoginRepository

class LoginRepositoryImpl(
    private val loginApiService: LoginApiService,
    private val sessionManager: SessionManager
) : LoginRepository {
    override suspend fun getLogin(email: String, password: String): Login {
        return when (val result = loginApiService.postLogin(email, password)) {
            is NetworkResult.Success -> {
                sessionManager.onLoginSuccess(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken
                )
                result.data.toDomain()
            }

            is NetworkResult.Error -> throw NetworkError.HttpError(
                statusCode = result.statusCode,
                errorCode = result.errorCode,
                message = result.message
            )
        }
    }
}
