package com.project.nexfor.feature.appointment.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.nexfor.core.ui.designsystem.components.AppScaffold
import com.project.nexfor.core.ui.designsystem.components.BottomBarConfig
import com.project.nexfor.core.ui.designsystem.components.BottomNavItem
import com.project.nexfor.core.ui.designsystem.components.TopBarConfig
import com.project.nexfor.core.ui.theme.NexForTheme
import com.project.nexfor.feature.appointment.model.DayUi
import com.project.nexfor.feature.appointment.model.toUi
import com.project.nexfor.feature.appointment.ui.components.DateSelector
import com.project.nexfor.feature.appointment.ui.components.DayTimeline
import kotlinx.datetime.LocalDate
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppointmentScreen(
    viewModel: AppointmentViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(AppointmentIntent.LoadAppointments)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AppointmentEffect.NavigateToDetail -> {}
                is AppointmentEffect.ShowError -> {}
            }
        }
    }

    AppointmentContent(
        state = state,
        onIntent = viewModel::onIntent
    )

}

@Composable
fun AppointmentContent(state: AppointmentState, onIntent: (AppointmentIntent) -> Unit) {
    val dayAppointments = remember(state.appointments, state.selectedDate) {
        state.appointments
            .map { it.toUi() }
            .filter { it.date == state.selectedDate }
    }

    AppScaffold(
        topBarConfig = TopBarConfig(title = "Appointments", showBack = true, onBackClick = {}),
        bottomBarConfig = BottomBarConfig(
            items = listOf(BottomNavItem.Schedule, BottomNavItem.Bill, BottomNavItem.Reports),
            currentRoute = BottomNavItem.Schedule.route,
            onItemClick = {}
        ),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* futuro intent: CreateAppointment */ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nueva cita")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Spacer(Modifier.height(16.dp))
            DateSelector(
                days = state.visibleDays,
                selectedDate = state.selectedDate,
                onDateSelected = { onIntent(AppointmentIntent.OnDateSelected(it)) }
            )
            Spacer(Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                DayTimeline(
                    startHour = 15,
                    endHour = 20,
                    appointments = dayAppointments,
                    onAppointmentClick = { /* ... */ },
                    onAvailableClick = { /* ... */ }
                )
            }
        }
    }
}


@Preview(name = "NexFor - Appointment Screen", showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun AppointmentScreenPreview() {
    NexForTheme {
        AppointmentContent(
            state = AppointmentState(
                selectedDate = LocalDate(2024, 8, 14),
                visibleDays = listOf(
                    DayUi(LocalDate(2024, 8, 12), "Mon", 12),
                    DayUi(LocalDate(2024, 8, 13), "Tue", 13),
                    DayUi(LocalDate(2024, 8, 14), "Wed", 14),
                    DayUi(LocalDate(2024, 8, 15), "Thu", 15),
                    DayUi(LocalDate(2024, 8, 16), "Fri", 16),
                    DayUi(LocalDate(2024, 8, 17), "Sat", 17),
                    DayUi(LocalDate(2024, 8, 18), "Sun", 18),
                )
            ),
            onIntent = {}
        )
    }
}