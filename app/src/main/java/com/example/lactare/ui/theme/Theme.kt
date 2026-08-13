package com.example.lactare.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val LactareScheme = darkColorScheme(
    primary = LactareBlue,
    secondary = LactareCyan,
    background = LactareBackground,
    surface = androidx.compose.ui.graphics.Color.White
)

@Composable
fun LactareTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LactareScheme,
        typography = Typography,
        content = content
    )
}
