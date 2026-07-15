package com.project.nexfor.testbaseui.list

import androidx.lifecycle.viewModelScope
import com.project.nexfor.designsystem.components.AppDialog
import com.project.nexfor.designsystem.components.LoadState
import com.project.nexfor.mvi.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * En un caso real, este ViewModel recibiría un repositorio/UseCase por
 * constructor (inyectado con Koin). Aquí se simula la carga con `delay()`
 * para que el ejemplo compile y se vea funcionando sin depender de una
 * capa de datos real.
 */
class TestListViewModel : BaseViewModel<TestListUiState, TestListIntent, TestListSideEffect>(
    initialState = TestListUiState()
) {

    /** Único punto de entrada desde la Screen. Mantener como simple "router". */
    override fun onIntent(intent: TestListIntent) {
        println("🟩 [INTENT] TestListViewModel -> $intent") // 🔍 DEMO
        when (intent) {
            is TestListIntent.LoadItems -> loadItems()
            is TestListIntent.Refresh -> loadItems()
            is TestListIntent.RequestDelete -> requestDelete(intent.item)
            is TestListIntent.ItemClicked ->
                sendEffect { TestListSideEffect.NavigateToDetail(intent.item.id) }

            is TestListIntent.ShowDialog -> showDialog()
        }
    }

    private fun showDialog() {
        viewModelScope.launch {
            setState { copy(loadState = LoadState.Loading) }
            try {
                delay(1200)
                // -----------------------------------------------------
                setState {
                    copy(
                        loadState = LoadState.Success, dialog = AppDialog.Success(
                        message = "El registro se guardó correctamente.",
                        onDismiss = {
                            dismissDialog()
                        }
                    ))
                }
            } catch (e: Exception) {
                setState { copy(loadState = LoadState.Error(e.message ?: "Error desconocido")) }
            }
        }
    }

    private fun loadItems() {
        viewModelScope.launch {
            setState { copy(loadState = LoadState.Loading) }
            try {
                // --- Simulación de llamada a repositorio/backend ---
                delay(1200)
                val result = fakeFetchItems()
                // -----------------------------------------------------
                setState { copy(loadState = LoadState.Success, items = result) }
            } catch (e: Exception) {
                setState { copy(loadState = LoadState.Error(e.message ?: "Error desconocido")) }
            }
        }
    }

    /** Antes de borrar algo destructivo, siempre se pide confirmación. */
    private fun requestDelete(item: TestItem) {
        setState {
            copy(
                dialog = AppDialog.Confirm(
                    title = "Eliminar item",
                    message = "¿Seguro que quieres eliminar \"${item.name}\"? Esta acción no se puede deshacer.",
                    onConfirm = { confirmDelete(item) },
                    onDismiss = { dismissDialog() }
                )
            )
        }
    }

    private fun confirmDelete(item: TestItem) {
        setState { copy(dialog = null, items = items.filterNot { it.id == item.id }) }
        sendEffect { TestListSideEffect.ShowSnackbar("\"${item.name}\" eliminado") }
    }

    private fun dismissDialog() {
        setState { copy(dialog = null) }
    }

    /** Datos falsos solo para que el ejemplo compile y se vea funcionando. */
    private fun fakeFetchItems(): List<TestItem> = listOf(
        TestItem("1", "Cliente A", "Bucaramanga"),
        TestItem("2", "Cliente B", "Floridablanca"),
        TestItem("3", "Cliente C", "Girón")
    )
}
