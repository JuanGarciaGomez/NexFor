package com.project.nexfor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.project.nexfor.feature.appointment.ui.AppointmentScreen
import com.project.nexfor.feature.appointment.ui.create.CreateAppointmentScreen
import com.project.nexfor.feature.auth.ui.LoginScreen
import com.project.nexfor.testbaseui.detail.TestDetailScreen
import com.project.nexfor.testbaseui.list.TestListScreen

@Composable
fun NexForNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login
    ) {
        composable<Screen.TestList> {
            TestListScreen(
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
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Appointments) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                }
            )
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

        composable<Screen.Appointments> {
            AppointmentScreen(
                onNavigateToCreate = {
                    navController.navigate(Screen.CreateAppointment)
                }
            )
        }

        composable<Screen.CreateAppointment> {
            CreateAppointmentScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
