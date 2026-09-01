package com.project.nexfor.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object TestList : Screen

    @Serializable
    data class TestDetail(val itemId: String) : Screen

    @Serializable
    data object Login : Screen

    @Serializable
    data object Schedule : Screen

    @Serializable
    data object Bill : Screen

    @Serializable
    data object Reports : Screen
    @Serializable
    data object Appointments : Screen

    @Serializable
    data object CreateAppointment : Screen
}
