package com.project.nexfor.core.ui.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Home : BottomNavItem("home", Icons.Filled.Home, "Inicio")
    data object Profile : BottomNavItem("profile", Icons.Filled.Person, "Perfil")
}

data class BottomBarConfig(
    val items: List<BottomNavItem>,
    val currentRoute: String,
    val onItemClick: (BottomNavItem) -> Unit
)

@Composable
fun AppBottomBar(config: BottomBarConfig) {
    NavigationBar {
        config.items.forEach { item ->
            NavigationBarItem(
                selected = item.route == config.currentRoute,
                onClick = { config.onItemClick(item) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
