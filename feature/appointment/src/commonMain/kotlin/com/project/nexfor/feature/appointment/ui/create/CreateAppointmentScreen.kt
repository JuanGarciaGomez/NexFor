package com.project.nexfor.feature.appointment.ui.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.nexfor.core.ui.designsystem.components.AppScaffold
import com.project.nexfor.core.ui.designsystem.components.TopBarConfig
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAppointmentScreen(
    viewModel: CreateAppointmentViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateAppointmentEffect.NavigateBack -> onBack()
                is CreateAppointmentEffect.ShowError -> {
                    // Aquí podrías mostrar un SnackBar con effect.message
                }
            }
        }
    }

    CreateAppointmentContent(
        state = state,
        onIntent = viewModel::onIntent,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAppointmentContent(
    state: CreateAppointmentState,
    onIntent: (CreateAppointmentIntent) -> Unit,
    onBack: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

        val datePickerState = rememberDatePickerState(
            selectableDates = object : SelectableDates {

                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val selectedDate = kotlin.time.Instant
                        .fromEpochMilliseconds(utcTimeMillis)
                        .toLocalDateTime(TimeZone.UTC)
                        .date

                    return selectedDate >= today
                }
            }
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val date = Instant.fromEpochMilliseconds(it)
                            .toLocalDateTime(TimeZone.UTC).date
                        onIntent(CreateAppointmentIntent.OnDateSelected(date))
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showStartTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = state.startTime?.hour ?: 9,
            initialMinute = state.startTime?.minute ?: 0
        )
        AppTimePickerDialog(
            onDismissRequest = { showStartTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    onIntent(
                        CreateAppointmentIntent.OnStartTimeSelected(
                            LocalTime(
                                timePickerState.hour,
                                timePickerState.minute
                            )
                        )
                    )
                    showStartTimePicker = false
                }) { Text("OK") }
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }

    if (showEndTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = state.endTime?.hour ?: 10,
            initialMinute = state.endTime?.minute ?: 0
        )
        AppTimePickerDialog(
            onDismissRequest = { showEndTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    onIntent(
                        CreateAppointmentIntent.OnEndTimeSelected(
                            LocalTime(
                                timePickerState.hour,
                                timePickerState.minute
                            )
                        )
                    )
                    showEndTimePicker = false
                }) { Text("OK") }
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }

    AppScaffold(
        topBarConfig = TopBarConfig(
            title = "Nueva Cita",
            showBack = true,
            onBackClick = onBack
        )
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Selector de Cliente
            SectionTitle("Seleccionar Cliente")

            var expanded by remember { mutableStateOf(false) }
            val selectedCustomer = state.customers.find { it.id == state.selectedCustomerId }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedCustomer?.name ?: "Seleccione un cliente",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    leadingIcon = { Icon(Icons.Default.Person, null) }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    state.customers.forEach { customer ->
                        DropdownMenuItem(
                            text = { Text(customer.name) },
                            onClick = {
                                onIntent(CreateAppointmentIntent.OnCustomerSelected(customer.id))
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(Modifier.height(16.dp))

            // Sección Fecha y Hora
            SectionTitle("Fecha y Hora")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = state.appointmentDate?.toString() ?: "",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Fecha") },
                    modifier = Modifier.weight(1f).clickable { showDatePicker = true },
                    enabled = false,
                    leadingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.CalendarMonth, null)
                        }
                    }
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = state.startTime?.let {
                        "${
                            it.hour.toString().padStart(2, '0')
                        }:${it.minute.toString().padStart(2, '0')}"
                    } ?: "",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Inicio") },
                    modifier = Modifier.weight(1f).clickable { showStartTimePicker = true },
                    enabled = false,
                    leadingIcon = {
                        IconButton(onClick = { showStartTimePicker = true }) {
                            Icon(Icons.Default.Schedule, null)
                        }
                    }
                )
                OutlinedTextField(
                    value = state.endTime?.let {
                        "${
                            it.hour.toString().padStart(2, '0')
                        }:${it.minute.toString().padStart(2, '0')}"
                    } ?: "",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Fin") },
                    modifier = Modifier.weight(1f).clickable { showEndTimePicker = true },
                    enabled = false,
                    leadingIcon = {
                        IconButton(onClick = { showEndTimePicker = true }) {
                            Icon(Icons.Default.Schedule, null)
                        }
                    }
                )
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(Modifier.height(16.dp))

            // Sección Servicios
            SectionTitle("Servicios")
            state.availableServices.forEach { service ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onIntent(CreateAppointmentIntent.OnServiceToggled(service.id)) }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = state.selectedServiceIds.contains(service.id),
                        onCheckedChange = {
                            onIntent(
                                CreateAppointmentIntent.OnServiceToggled(
                                    service.id
                                )
                            )
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(text = service.name, style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = "Empleado ID: ${service.employeeId}",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(Modifier.height(16.dp))

            // Notas
            OutlinedTextField(
                value = state.notes,
                onValueChange = { onIntent(CreateAppointmentIntent.OnNotesChanged(it)) },
                label = { Text("Notas de la cita") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = { onIntent(CreateAppointmentIntent.SaveAppointment) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.selectedCustomerId.isNotEmpty() && state.selectedServiceIds.isNotEmpty()
                ) {
                    Text("Crear Cita")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
    )
}

@Composable
fun AppTimePickerDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        dismissButton = {
            TextButton(onClick = onDismissRequest) { Text("Cancelar") }
        },
        text = { content() }
    )
}
