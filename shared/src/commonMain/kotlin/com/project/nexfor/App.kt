package com.project.nexfor

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.nexfor.testbaseui.list.TestListScreen
import com.project.nexfor.testbaseui.list.TestListViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel: TestListViewModel = viewModel { TestListViewModel() }

        TestListScreen(
            viewModel = viewModel,
            onBackClick = { /* no hace nada por ahora, es solo prueba */ },
            onNavigateToDetail = { itemId ->
                // Aquí normalmente navegarías a detalle. Por ahora, vacío.
            }
        )
    }
}