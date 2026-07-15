package com.project.nexfor.core.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ============================================================================
 *  BASE VIEWMODEL MVI
 * ============================================================================
 * Flujo:
 *   1. Screen dispara:      viewModel.onIntent(Intent.LoadItems)
 *   2. ViewModel actualiza: setState { copy(loadState = LoadState.Loading) }
 *   3. Screen observa `state` (StateFlow) y se recompone sola.
 *   4. Para algo de un solo disparo: sendEffect { Effect.ShowSnackbar(...) }
 *   5. Screen colecta `effect` en un LaunchedEffect y reacciona una vez.
 * ============================================================================
 */
abstract class BaseViewModel<S : UiState, I : UiIntent, E : UiEffect>(
    initialState: S
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()
    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    abstract fun onIntent(intent: I)
    protected fun setState(reducer: S.() -> S) {
        _state.update(reducer)
        println("🟦 [STATE] ${this::class.simpleName} -> ${_state.value}")
    }

    protected fun currentState(): S = _state.value
    protected fun sendEffect(builder: () -> E) {
        viewModelScope.launch {
            val effect = builder()
            println("🟨 [EFFECT] ${this::class.simpleName} -> $effect")
            _effect.send(effect)
        }
    }
}
