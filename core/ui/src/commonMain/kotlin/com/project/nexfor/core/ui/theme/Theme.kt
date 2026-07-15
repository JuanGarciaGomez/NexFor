package com.project.nexfor.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val NexForDarkColors = darkColorScheme(
    primary = Amber,
    onPrimary = OnAmber,
    secondary = Teal,          // data accent mapped to M3 "secondary"
    onSecondary = OnTeal,
    background = Background,
    onBackground = Foreground,
    surface = Card,
    onSurface = Foreground,
    surfaceVariant = Secondary,
    onSurfaceVariant = MutedForeground,
    error = Destructive,
    onError = OnDestructive,
    outline = Border,
    outlineVariant = Border,
)

// Colors that fall outside the standard Material 3 scheme
@Immutable
data class NexForExtraColors(
    val teal: Color = Teal,
    val onTeal: Color = OnTeal,
    val cardElevated: Color = CardElevated,
    val mutedForeground: Color = MutedForeground,
    val border: Color = Border,
)

val LocalNexForColors = staticCompositionLocalOf { NexForExtraColors() }

@Composable
fun NexForTheme(
    // The app is dark-first; the system theme is intentionally ignored.
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = NexForDarkColors,
        typography = NexForTypography,
        shapes = NexForShapes,
    ) {
        CompositionLocalProvider(
            LocalNexForColors provides NexForExtraColors()
        ) {
            content()
        }
    }
}

// Shorthand access: NexForTheme.extra.teal
object NexForTheme {
    val extra: NexForExtraColors
        @Composable get() = LocalNexForColors.current
}