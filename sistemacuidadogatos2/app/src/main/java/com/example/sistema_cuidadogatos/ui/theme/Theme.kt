package com.example.sistema_cuidadogatos.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    tertiary = TertiaryLight,
    background = BackgroundDark,
    surface = BackgroundDark,
    onPrimary = OnPrimaryDark,
    onSecondary = OnPrimaryDark,
    onBackground = OnPrimaryDark,
    onSurface = OnPrimaryDark,
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight, // Baby Blue
    secondary = SecondaryLight, // Sky Blue
    tertiary = TertiaryLight,
    background = BackgroundLight, // Quase Branco
    surface = SurfaceLight, // Quase Branco para Cards
    onPrimary = OnPrimaryLight, // Azul Escuro para Texto
    onSecondary = OnPrimaryLight,
    onBackground = OnBackgroundLight,
    onSurface = OnBackgroundLight,
)

@Composable
fun CuiGatoTheme( // ⚠️ Esta é a função que resolve o erro na MainActivity!
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Desabilitamos Dynamic Color para manter a estética fofa consistente.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Assume Typography.kt está definido e importado
        content = content
    )
}