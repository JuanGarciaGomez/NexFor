package com.project.nexfor.core.ui.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.project.nexfor.core.ui.theme.NexForTheme

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Schedule : BottomNavItem("schedule", Icons.Filled.CalendarMonth, "Calendario")
    data object Bill : BottomNavItem("bill", Icons.Filled.AccountBalance, "Facturas")
    data object Reports : BottomNavItem("reports", Icons.Filled.AccountBalanceWallet, "Reporte")
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

@Preview(name = "NexFor - AppBottomBar", showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun AppBottomBarPreview() {
    NexForTheme {
        AppBottomBar(
            config = BottomBarConfig(
                items = listOf(BottomNavItem.Schedule, BottomNavItem.Bill, BottomNavItem.Reports),
                currentRoute = BottomNavItem.Schedule.route,
                onItemClick = {}
            )
        )
    }
}