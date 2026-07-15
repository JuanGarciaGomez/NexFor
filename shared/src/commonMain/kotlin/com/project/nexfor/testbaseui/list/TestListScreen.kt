package com.project.nexfor.testbaseui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.nexfor.core.ui.designsystem.components.AppDialogHost
import com.project.nexfor.core.ui.designsystem.components.AppScaffold
import com.project.nexfor.core.ui.designsystem.components.EmptyStateView
import com.project.nexfor.core.ui.designsystem.components.ErrorStateView
import com.project.nexfor.core.ui.designsystem.components.LoadState
import com.project.nexfor.core.ui.designsystem.components.TopBarConfig
import com.project.nexfor.core.ui.designsystem.AppSpacing
import com.project.nexfor.core.ui.designsystem.components.AppButton
import com.project.nexfor.core.ui.designsystem.components.AppOutlinedButton
import com.project.nexfor.core.ui.designsystem.components.BottomBarConfig
import com.project.nexfor.core.ui.designsystem.components.BottomNavItem
import kotlinx.coroutines.launch

/**
 * ============================================================================
 *  SCREEN: TestListScreen
 * ============================================================================
 * Reglas que sigue esta Screen (aplican a toda pantalla de la app):
 *  1. Nunca contiene lógica de negocio, solo lee `state` y despacha `Intent`.
 *  2. Los efectos se colectan UNA vez en un LaunchedEffect(Unit).
 *  3. El diálogo se pinta con un solo AppDialogHost(state.dialog).
 *  4. El contenido varía según state.loadState (Loading/Error/Success vacío
 *     o con datos) — este patrón se repite en cualquier pantalla de listado.
 *
 * `onBackClick` y `onNavigateToDetail` se reciben como parámetros porque la
 * navegación real (Voyager/Decompose/Navigation Compose) se conecta desde
 * fuera de este ejemplo.
 * ============================================================================
 */
@Composable
fun TestListScreen(
    viewModel: TestListViewModel,
    onBackClick: () -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.onIntent(TestListIntent.LoadItems)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TestListSideEffect.ShowSnackbar ->
                    scope.launch { snackBarHostState.showSnackbar(effect.message) }

                is TestListSideEffect.NavigateToDetail -> onNavigateToDetail(effect.itemId)
            }
        }
    }

    AppScaffold(
        topBarConfig = TopBarConfig(title = "Clientes", showBack = true, onBackClick = onBackClick),
        bottomBarConfig = BottomBarConfig(
            items = listOf(BottomNavItem.Home, BottomNavItem.Profile),
            currentRoute = "",
            onItemClick = {}),
        isLoading = state.loadState is LoadState.Loading && state.items.isEmpty(),
        snackBarHostState = snackBarHostState,
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: navegar a crear item */ }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        AppDialogHost(dialog = state.dialog)

        when (val loadState = state.loadState) {
            is LoadState.Error -> ErrorStateView(
                message = loadState.message,
                onRetry = { viewModel.onIntent(TestListIntent.Refresh) },
                modifier = Modifier.padding(padding)
            )

            is LoadState.Success -> {
                if (state.items.isEmpty()) {
                    EmptyStateView(
                        message = "Aún no tienes clientes registrados",
                        actionLabel = "Crear cliente",
                        onAction = { /* TODO: navegar a crear item */ },
                        modifier = Modifier.padding(padding)
                    )
                } else {
                    Column(modifier = Modifier.fillMaxWidth().padding(paddingValues = padding)) {
                        TestItemList(
                            items = state.items,
                            modifier = Modifier.weight(1f).padding(padding),
                            onItemClick = { viewModel.onIntent(TestListIntent.ItemClicked(it)) },
                            onDeleteClick = { viewModel.onIntent(TestListIntent.RequestDelete(it)) }
                        )
                        AppButton(
                            text = "Test dialog",
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                            onClick = {
                                viewModel.onIntent(TestListIntent.ShowDialog)
                            })
                        AppOutlinedButton(
                            text = "Test Outline",
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                            onClick = {})
                    }
                }
            }

            // Idle -> aún no se disparó LoadItems. Loading -> ya lo pinta el overlay.
            LoadState.Idle, LoadState.Loading -> Unit
        }
    }
}

@Composable
private fun TestItemList(
    items: List<TestItem>,
    modifier: Modifier = Modifier,
    onItemClick: (TestItem) -> Unit,
    onDeleteClick: (TestItem) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(AppSpacing.md)
    ) {
        items(items, key = { it.id }) { item ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = AppSpacing.xs)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppSpacing.md)
                        .clickable { onItemClick(item) },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = item.description, style = MaterialTheme.typography.bodySmall)
                    }
                    IconButton(onClick = { onDeleteClick(item) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
