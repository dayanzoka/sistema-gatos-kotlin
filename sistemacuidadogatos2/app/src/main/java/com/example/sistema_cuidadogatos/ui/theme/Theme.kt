package com.example.sistema_cuidadogatos.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = BrownLight,        // Ícones principais (Marrom Claro no escuro)
    onPrimary = BrownCute,       // Texto dentro do botão primary
    secondary = GreenBabyDark,   // Verde mais escuro
    onSecondary = BrownCute,
    tertiary = GreenBaby,
    background = Color.Transparent, // Transparente para ver o wallpaper
    surface = GreenBabyDark,     // Cards verdes
    onSurface = BrownCute,       // Texto Marrom
    error = ErrorRed,
    onError = Color.White
)

// Definição do Esquema Claro (Light Mode - O principal para seu tema fofo)
private val LightColorScheme = lightColorScheme(
    primary = BrownCute,         // Ícones principais (Marrom Escuro)
    onPrimary = Color.White,     // Texto dentro do botão primary
    secondary = GreenBaby,       // Verde Bebê
    onSecondary = BrownCute,
    tertiary = BrownLight,
    background = Color.Transparent, // Transparente
    surface = GreenBaby,         // Cards verdes
    onSurface = BrownCute,       // Texto Marrom
    error = ErrorRed,
    onError = Color.White
)

@Composable
fun CuiGatoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Barra de status transparente
            window.statusBarColor = Color.Transparent.toArgb()
            // Ícones da barra de status escuros (para ver no papel de parede claro)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}