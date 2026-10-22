package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MinimalDarkColorScheme = darkColorScheme(
    primary = MinimalDarkAccent,
    onPrimary = MinimalDarkBg,
    primaryContainer = MinimalDarkCard,
    onPrimaryContainer = MinimalDarkText,
    secondary = MinimalDarkMuted,
    onSecondary = MinimalDarkBg,
    background = MinimalDarkBg,
    onBackground = MinimalDarkText,
    surface = MinimalDarkSurface,
    onSurface = MinimalDarkText,
    surfaceVariant = MinimalDarkCard,
    onSurfaceVariant = MinimalDarkMuted,
    outline = MinimalDarkBorder,
    error = MinimalRed,
    onError = MinimalDarkText
)

private val MinimalMilkyColorScheme = lightColorScheme(
    primary = MinimalMilkAccent,
    onPrimary = MinimalMilkSurface,
    primaryContainer = MinimalMilkCard,
    onPrimaryContainer = MinimalMilkText,
    secondary = MinimalMilkMuted,
    onSecondary = MinimalMilkSurface,
    background = MinimalMilkBg,
    onBackground = MinimalMilkText,
    surface = MinimalMilkSurface,
    onSurface = MinimalMilkText,
    surfaceVariant = MinimalMilkCard,
    onSurfaceVariant = MinimalMilkMuted,
    outline = MinimalMilkBorder,
    error = MinimalRed,
    onError = MinimalMilkSurface
)

@Composable
fun AutoBrowserTheme(
    nightMode: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (nightMode) MinimalDarkColorScheme else MinimalMilkyColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
