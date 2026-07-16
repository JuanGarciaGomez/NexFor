package com.project.nexfor

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.project.nexfor.core.ui.theme.NexForTheme
import com.project.nexfor.navigation.NexForNavGraph

@Composable
fun App() {
    NexForTheme {
        val navController = rememberNavController()
        NexForNavGraph(navController = navController)
    }
}
