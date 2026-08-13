package com.project.nexfor.domain.login.usecase

import com.project.nexfor.domain.login.model.Login
import com.project.nexfor.domain.login.repository.LoginRepository

class LoginUseCase(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(): Login {
        return repository.getLogin()
    }
}
