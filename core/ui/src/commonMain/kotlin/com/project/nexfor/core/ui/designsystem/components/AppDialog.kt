package com.project.nexfor.core.ui.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
sealed class AppDialog {
    data class Error(
        val title: String = "Ocurrió un error",
        val message: String,
        val onDismiss: () -> Unit
    ) : AppDialog()

    data class Success(
        val title: String = "¡Listo!",
        val message: String,
        val onDismiss: () -> Unit
    ) : AppDialog()

    data class Confirm(
        val title: String,
        val message: String,
        val confirmLabel: String = "Confirmar",
        val dismissLabel: String = "Cancelar",
        val onConfirm: () -> Unit,
        val onDismiss: () -> Unit
    ) : AppDialog()
}

@Composable
fun AppDialogHost(dialog: AppDialog?) {
    when (dialog) {
        is AppDialog.Error -> AppMessageDialog(
            title = dialog.title,
            message = dialog.message,
            icon = Icons.Default.Error,
            iconTint = MaterialTheme.colorScheme.error,
            onDismiss = dialog.onDismiss
        )

        is AppDialog.Success -> AppMessageDialog(
            title = dialog.title,
            message = dialog.message,
            icon = Icons.Default.CheckCircle,
            iconTint = Color(0xFF4CAF50),
            onDismiss = dialog.onDismiss
        )

        is AppDialog.Confirm -> AppConfirmDialog(dialog)

        null -> Unit
    }
}

@Composable
private fun AppMessageDialog(
    title: String,
    message: String,
    icon: ImageVector,
    iconTint: Color,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(icon, contentDescription = null, tint = iconTint) },
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Aceptar") } }
    )
}

@Composable
private fun AppConfirmDialog(dialog: AppDialog.Confirm) {
    AlertDialog(
        onDismissRequest = dialog.onDismiss,
        icon = { Icon(Icons.Default.Warning, contentDescription = null) },
        title = { Text(dialog.title) },
        text = { Text(dialog.message) },
        confirmButton = { TextButton(onClick = dialog.onConfirm) { Text(dialog.confirmLabel) } },
        dismissButton = { TextButton(onClick = dialog.onDismiss) { Text(dialog.dismissLabel) } }
    )
}
