package com.project.nexfor.core.ui.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.project.nexfor.core.ui.theme.NexForTheme

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

@OptIn(ExperimentalMaterial3Api::class)
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


@Preview(name = "NexFor - TopBar", showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun AppTopBarPreview() {
    NexForTheme {
        AppTopBar(
            config = TopBarConfig(
                title = "NexForTitle",
                showBack = true,
                onBackClick = {},
                actions = listOf(
                    TopBarAction(
                        icon = Icons.Default.Delete,
                        contentDescription = "",
                        onClick = {}
                    ),
                    TopBarAction(
                        icon = Icons.Default.Add,
                        contentDescription = "",
                        onClick = {}
                    ))
            )
        )
    }
}
