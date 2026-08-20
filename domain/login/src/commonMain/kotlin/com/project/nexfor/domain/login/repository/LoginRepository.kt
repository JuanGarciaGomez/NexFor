package com.project.nexfor.domain.login.repository

import com.project.nexfor.domain.login.model.Login

interface LoginRepository {
    suspend fun getLogin(email: String, password: String): Login
}
