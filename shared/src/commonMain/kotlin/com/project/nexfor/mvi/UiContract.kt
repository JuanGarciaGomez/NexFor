package com.project.nexfor.mvi

/**
 * ============================================================================
 *  CONTRATO BASE DE MVI
 * ============================================================================
 *  - UiState  -> snapshot inmutable de lo que la pantalla debe pintar
 *  - UiIntent -> acción/evento que disparó el usuario
 *  - UiEffect -> evento de un solo disparo (navegación, snackbar, etc.)
 * ============================================================================
 */
interface UiState
interface UiIntent
interface UiEffect

/**
 * Efectos comunes reutilizables entre features. Cada feature puede usar
 * estos directamente en su `sealed interface Effect : UiEffect` o definir
 * los suyos propios si necesita algo más específico.
 */
sealed interface BaseEffect : UiEffect {
    data class ShowSnackBar(val message: String, val isError: Boolean = false) : BaseEffect
    data object NavigateBack : BaseEffect
}
