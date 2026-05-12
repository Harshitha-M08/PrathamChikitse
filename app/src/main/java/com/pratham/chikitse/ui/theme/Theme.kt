package com.pratham.chikitse.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Red700 = Color(0xFFD32F2F)
val Red900 = Color(0xFFB71C1C)
val Orange600 = Color(0xFFFB8C00)
val Amber50  = Color(0xFFFFF8E1)
val White    = Color(0xFFFFFFFF)
val Grey100  = Color(0xFFF5F5F5)
val Grey800  = Color(0xFF424242)

private val LightColors = lightColorScheme(
    primary        = Red700,
    onPrimary      = White,
    primaryContainer = Color(0xFFFFDAD6),
    secondary      = Orange600,
    onSecondary    = White,
    background     = Grey100,
    surface        = White,
    onBackground   = Grey800,
    onSurface      = Grey800,
    error          = Red900
)

private val DarkColors = darkColorScheme(
    primary        = Color(0xFFFFB4AB),
    onPrimary      = Color(0xFF690005),
    primaryContainer = Red900,
    secondary      = Orange600,
    onSecondary    = White,
    background     = Color(0xFF1C1B1F),
    surface        = Color(0xFF2C2C2C),
    onBackground   = Color(0xFFE6E1E5),
    onSurface      = Color(0xFFE6E1E5)
)

@Composable
fun PrathamChikitseTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
