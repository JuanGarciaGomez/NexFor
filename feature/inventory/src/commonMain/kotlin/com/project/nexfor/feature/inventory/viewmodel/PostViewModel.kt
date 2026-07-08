package com.project.nexfor.feature.inventory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.nexfor.domain.catalog.usecase.GetPostUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing Post data.
 */
class PostViewModel(
    private val getPostUseCase: GetPostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PostUiState>(PostUiState.Idle)
    /**
     * Observable state for the UI.
     */
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    /**
     * Triggers the post loading process.
     */
    fun loadPost() {
        viewModelScope.launch {
            _uiState.value = PostUiState.Loading
            try {
                val post = getPostUseCase()
                _uiState.value = PostUiState.Success(post.title)
            } catch (e: Exception) {
                _uiState.value = PostUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
