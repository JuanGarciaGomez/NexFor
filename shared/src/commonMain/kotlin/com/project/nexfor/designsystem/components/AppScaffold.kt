package com.project.nexfor.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun AppScaffold(
    topBarConfig: TopBarConfig? = TopBarConfig(),
    bottomBarConfig: BottomBarConfig? = null,
    isLoading: Boolean = false,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = { topBarConfig?.let { AppTopBar(it) } },
        bottomBar = { bottomBarConfig?.let { AppBottomBar(it) } },
        floatingActionButton = floatingActionButton,
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        Box(Modifier.fillMaxSize()) {
            content(padding)
            if (isLoading) LoadingOverlay()
        }
    }
}