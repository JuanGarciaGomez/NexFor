package com.project.nexfor.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.project.nexfor.testbaseui.detail.TestDetailScreen
import com.project.nexfor.testbaseui.list.TestListScreen
import com.project.nexfor.testbaseui.list.TestListViewModel

@Composable
fun NexForNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.TestList
    ) {
        composable<Screen.TestList> {
            val viewModel: TestListViewModel = viewModel { TestListViewModel() }
            TestListScreen(
                viewModel = viewModel,
                onBackClick = { /* Handle exit if needed */ },
                onNavigateToDetail = { id ->
                    navController.navigate(Screen.TestDetail(itemId = id))
                }
            )
        }

        composable<Screen.TestDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.TestDetail>()
            TestDetailScreen(
                itemId = route.itemId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<Screen.Login> {
            // TODO: Implement Login screen
        }

        composable<Screen.Schedule> {
            // TODO: Implement Schedule screen
        }

        composable<Screen.Bill> {
            // TODO: Implement Bill screen
        }

        composable<Screen.Reports> {
            // TODO: Implement Reports screen
        }
    }
}
