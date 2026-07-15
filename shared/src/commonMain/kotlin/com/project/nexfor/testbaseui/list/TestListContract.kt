package com.project.nexfor.testbaseui.list

import com.project.nexfor.core.ui.designsystem.components.AppDialog
import com.project.nexfor.core.ui.designsystem.components.LoadState
import com.project.nexfor.core.ui.mvi.UiEffect
import com.project.nexfor.core.ui.mvi.UiIntent
import com.project.nexfor.core.ui.mvi.UiState

/**
 * ============================================================================
 *  CONTRACT: TestListScreen
 * ============================================================================
 * EJEMPLO: pantalla de listado. Úsalo como plantilla para cualquier
 * "listado de X" (clientes, POIs, gastos, etc).
 *
 * Las 3 piezas de MVI para esta pantalla van juntas en un solo archivo
 * (State + Intent + SideEffect), porque son las 3 caras de UN mismo
 * contrato: se leen y se mantienen mejor estando una al lado de la otra
 * en vez de saltar entre 3 archivos distintos.
 * ============================================================================
 */

/** Snapshot inmutable de todo lo que la pantalla necesita para pintarse. */
data class TestListUiState(
    val loadState: LoadState = LoadState.Idle,
    val items: List<TestItem> = emptyList(),
    val dialog: AppDialog? = null
) : UiState
data class TestItem(
    val id: String,
    val name: String,
    val description: String
)

sealed interface TestListIntent : UiIntent {
    data object LoadItems : TestListIntent
    data object Refresh : TestListIntent
    data object ShowDialog : TestListIntent
    data class RequestDelete(val item: TestItem) : TestListIntent
    data class ItemClicked(val item: TestItem) : TestListIntent
}
sealed interface TestListSideEffect : UiEffect {
    data class ShowSnackbar(val message: String) : TestListSideEffect
    data class NavigateToDetail(val itemId: String) : TestListSideEffect
}
