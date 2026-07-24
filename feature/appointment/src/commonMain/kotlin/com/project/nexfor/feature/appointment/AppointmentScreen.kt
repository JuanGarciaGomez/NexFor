package com.project.nexfor.feature.appointment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.nexfor.domain.appointment.model.Appointment

@Composable
fun AppointmentScreen(
    viewModel: AppointmentViewModel
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(AppointmentContract.Intent.LoadAppointments)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AppointmentContract.Effect.ShowError -> {
                    // Handle error (e.g. snackbar)
                }
                is AppointmentContract.Effect.NavigateToDetail -> {
                    // Navigate
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        state.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.appointments) { appointment ->
                AppointmentItem(
                    appointment = appointment,
                    onClick = { viewModel.onIntent(AppointmentContract.Intent.OnAppointmentClick(appointment)) }
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun AppointmentItem(
    appointment: Appointment,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(text = appointment.title, style = MaterialTheme.typography.titleMedium)
        Text(text = appointment.date, style = MaterialTheme.typography.bodySmall)
        Text(text = appointment.status, style = MaterialTheme.typography.bodyMedium)
    }
}
