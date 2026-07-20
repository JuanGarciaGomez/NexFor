package com.project.nexfor.feature.auth.ui

import androidx.lifecycle.viewModelScope
import com.project.nexfor.core.ui.designsystem.components.LoadState
import com.project.nexfor.core.ui.mvi.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel<LoginState, LoginIntent, LoginEffect>(
    initialState = LoginState()
) {
    override fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> setState { copy(email = intent.value) }
            is LoginIntent.Submit -> handleSubmit()
            LoginIntent.BiometricSubmit -> {}
            LoginIntent.ForgotPasswordClicked -> {}
            is LoginIntent.PasswordChanged -> {}
            is LoginIntent.RememberMeToggled -> {}
            is LoginIntent.RoleChanged -> {}
        }
    }

    private fun handleSubmit() {
        viewModelScope.launch {
            setState { copy(loadState = LoadState.Loading) }
            delay(1500)

            val email = currentState().email
            if (email.isBlank()) {
                sendEffect { LoginEffect.ShowError("Email cannot be empty") }
                setState { copy(loadState = LoadState.Idle) }
            } else {
                setState { copy(loadState = LoadState.Success) }
                sendEffect { LoginEffect.NavigateToDashboard }
            }
        }
    }
}