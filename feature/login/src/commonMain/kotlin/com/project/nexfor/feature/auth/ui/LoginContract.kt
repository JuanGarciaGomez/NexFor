package com.project.nexfor.feature.auth.ui

import com.project.nexfor.core.ui.designsystem.components.LoadState
import com.project.nexfor.core.ui.mvi.UiEffect
import com.project.nexfor.core.ui.mvi.UiIntent
import com.project.nexfor.core.ui.mvi.UiState

data class LoginState(
    val email: String = "",
    val password: String = "",
    val role: String = "Staff",
    val rememberMe: Boolean = false,
    val loadState: LoadState = LoadState.Idle
) : UiState

sealed interface LoginIntent : UiIntent {
    data class EmailChanged(val value: String) : LoginIntent
    data class PasswordChanged(val value: String) : LoginIntent
    data class RoleChanged(val role: String) : LoginIntent
    data class RememberMeToggled(val checked: Boolean) : LoginIntent
    data object ForgotPasswordClicked : LoginIntent
    data object Submit : LoginIntent
    data object BiometricSubmit : LoginIntent
}

sealed interface LoginEffect : UiEffect {
    data object NavigateToDashboard : LoginEffect
    data object NavigateToForgotPassword : LoginEffect
    data class ShowError(val message: String) : LoginEffect
}