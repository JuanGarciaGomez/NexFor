package com.project.nexfor.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class TopBarConfig(
    val title: String = "",
    val showBack: Boolean = false,
    val onBackClick: (() -> Unit)? = null,
    val actions: List<TopBarAction> = emptyList()
)

data class TopBarAction(
    val icon: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit
)

@Composable
fun AppTopBar(config: TopBarConfig) {
    TopAppBar(
        title = { Text(config.title) },
        navigationIcon = {
            if (config.showBack) {
                IconButton(onClick = { config.onBackClick?.invoke() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                }
            }
        },
        actions = {
            config.actions.forEach { action ->
                IconButton(onClick = action.onClick) {
                    Icon(action.icon, contentDescription = action.contentDescription)
                }
            }
        }
    )
}
