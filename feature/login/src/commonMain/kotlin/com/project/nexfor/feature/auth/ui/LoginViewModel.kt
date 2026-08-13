package com.project.nexfor.feature.auth.ui

import androidx.lifecycle.viewModelScope
import com.project.nexfor.core.ui.designsystem.components.LoadState
import com.project.nexfor.core.ui.mvi.BaseViewModel
import com.project.nexfor.domain.login.usecase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginState, LoginIntent, LoginEffect>(
    initialState = LoginState()
) {
    override fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> setState { copy(email = intent.value) }
            is LoginIntent.PasswordChanged -> setState { copy(password = intent.value) }
            is LoginIntent.RememberMeToggled -> setState { copy(rememberMe = intent.checked) }
            is LoginIntent.RoleChanged -> setState { copy(role = intent.role) }
            LoginIntent.Submit -> handleSubmit()
            LoginIntent.BiometricSubmit -> {}
            LoginIntent.ForgotPasswordClicked -> sendEffect { LoginEffect.NavigateToForgotPassword }
        }
    }

    private fun handleSubmit() {
        val email = currentState().email
        val password = currentState().password

        if (email.isBlank() || password.isBlank()) {
            sendEffect { LoginEffect.ShowError("Email y contraseña son obligatorios") }
            return
        }

        viewModelScope.launch {
            setState { copy(loadState = LoadState.Loading) }
            runCatching {
                loginUseCase(email = email, password = password)
            }.onSuccess { login ->
                setState { copy(loadState = LoadState.Success) }
                sendEffect { LoginEffect.NavigateToDashboard }
            }.onFailure { error ->
                setState { copy(loadState = LoadState.Idle) }
                sendEffect { LoginEffect.ShowError(error.message ?: "Error al iniciar sesión") }
            }
        }
    }
}