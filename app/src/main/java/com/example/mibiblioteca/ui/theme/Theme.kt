package com.example.mibiblioteca.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


/**
  Archivo de tema de la aplicación MiBiblioteca.
  Define los esquemas de colores para modos claro y oscuro,
  y proporciona el composable MiBibliotecaTheme que aplica
  MaterialTheme con el esquema de colores seleccionado,
  la tipografía y el contenido de la UI.
 */



// ——— Light Color Scheme ———
private val LightColorScheme = lightColorScheme(
    primary           = Color(0xFF4A90E2),
    onPrimary         = Color.White,
    primaryContainer  = Color(0xFFB3CDE8),

    secondary         = Color(0xFF50E3C2),
    onSecondary       = Color.Black,
    secondaryContainer= Color(0xFFB2E8DE),

    background        = Color(0xFFF6F6F6),
    onBackground      = Color.Black,

    surface           = Color.White,
    onSurface         = Color.Black,
)

// ——— Dark Color Scheme ———
private val DarkColorScheme = darkColorScheme(
    primary           = Color(0xFF1C3D5A),
    onPrimary         = Color.Black,
    primaryContainer  = Color(0xFF3A5A7A),

    secondary         = Color(0xFF50E3C2),
    onSecondary       = Color.Black,
    secondaryContainer= Color(0xFFB2E8DE),

    background        = Color(0xFF121212),
    onBackground      = Color.White,

    surface           = Color(0xFF1E1E1E),
    onSurface         = Color.White,
)

@Composable
fun MiBibliotecaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // desactiva Material You si no lo usas
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val ctx = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(ctx) else dynamicLightColorScheme(ctx)
        }
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}
