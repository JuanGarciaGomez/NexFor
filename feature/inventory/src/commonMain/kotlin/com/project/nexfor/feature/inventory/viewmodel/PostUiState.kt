package com.project.nexfor.feature.inventory.viewmodel

/**
 * Represents the UI state for the Post screen.
 */
sealed class PostUiState {
    /**
     * Initial state.
     */
    data object Idle : PostUiState()

    /**
     * Data is being fetched.
     */
    data object Loading : PostUiState()

    /**
     * Data successfully fetched.
     */
    data class Success(val title: String) : PostUiState()

    /**
     * An error occurred.
     */
    data class Error(val message: String) : PostUiState()
}
