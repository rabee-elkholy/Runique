package com.plcoding.core.presentation.designsystem

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Define the color scheme for the dark theme.
val DarkColorScheme = darkColorScheme(
    primary = RuniqueGreen,
    background = RuniqueBlack,
    surface = RuniqueDarkGray,
    secondary = RuniqueWhite,
    tertiary = RuniqueWhite,
    primaryContainer = RuniqueGreen30,
    onPrimary = RuniqueBlack,
    onBackground = RuniqueWhite,
    onSurface = RuniqueWhite,
    onSurfaceVariant = RuniqueGray
)

/**
 * Applies the custom Runique theme to the provided content.
 *
 * This theme uses a dark color scheme and sets up the system UI elements, such as the status bar,
 * to match the theme. It also handles setting light or dark appearance for the status bar icons.
 *
 * @param content The Composable content that will use the theme.
 */
@Composable
fun RuniqueTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current

    // Apply side effects related to the theme if not in edit mode.
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Ensure the status bar icons are visible on the dark background.
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    // Apply the theme to the content.
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
