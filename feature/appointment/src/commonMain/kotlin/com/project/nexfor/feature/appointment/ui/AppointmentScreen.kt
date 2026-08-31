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
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
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
            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                DayTimeline(
                    startHour = 8,
                    endHour = 22,
                    appointments = dayAppointments,
                    onAppointmentClick = { /* ... */ },
                    onAvailableClick = { /* ... */ }
                )
            }
        }
    }
}

@Preview(name = "Appointment Screen - Confirmed State", showBackground = true)
@Composable
fun AppointmentScreenPreview() {
    val mockDate = LocalDate(2026, 8, 15)
    val mockAppointments = listOf(
        com.project.nexfor.domain.appointment.model.Appointment(
            id = "1",
            appointmentDate = "2026-08-15",
            startTime = "08:45:00",
            endTime = "09:15:00",
            status = "confirmed",
            customerName = "Juan Pérez",
            services = listOf("Corte ejecutivo", "Barba")
        ),
        com.project.nexfor.domain.appointment.model.Appointment(
            id = "2",
            appointmentDate = "2026-08-15",
            startTime = "10:30:00",
            endTime = "11:30:00",
            status = "pending",
            customerName = "Andrés Gómez",
            services = listOf("Limpieza facial")
        )
    )

    NexForTheme {
        AppointmentContent(
            state = AppointmentState(
                appointments = mockAppointments,
                selectedDate = mockDate,
                visibleDays = (-2..4).map { offset -> 
                    DayUi(mockDate.plus(offset, DateTimeUnit.DAY), "Day", 10)
                }
            ),
            onIntent = {}
        )
    }
}