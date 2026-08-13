package com.example.lactare.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val LactareDarkScheme = darkColorScheme(
    primary = AccentBlue,
    onPrimary = DarkBackground,
    secondary = AccentBlueMuted,
    onSecondary = TextPrimary,
    background = DarkBackground,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
    error = ErrorRed,
    onError = TextPrimary
)

@Composable
fun LactareTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LactareDarkScheme,
        typography = Typography,
        content = content
    )
}
