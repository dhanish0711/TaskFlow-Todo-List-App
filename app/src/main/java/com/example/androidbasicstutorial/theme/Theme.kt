package com.example.androidbasicstutorial.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class ThemePalette {
    Indigo, Teal, Pink, Amber
}

private fun getDarkColorScheme(palette: ThemePalette): ColorScheme {
    return when (palette) {
        ThemePalette.Indigo -> darkColorScheme(
            primary = IndigoPrimary,
            secondary = IndigoSecondary,
            tertiary = IndigoTertiary,
            background = IndigoDarkBackground,
            surface = IndigoDarkSurface,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFFE2E8F0),
            onSurface = Color(0xFFF1F5F9)
        )
        ThemePalette.Teal -> darkColorScheme(
            primary = TealPrimary,
            secondary = TealSecondary,
            tertiary = TealTertiary,
            background = TealDarkBackground,
            surface = TealDarkSurface,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFFCCFBF1),
            onSurface = Color(0xFFF0FDFA)
        )
        ThemePalette.Pink -> darkColorScheme(
            primary = PinkPrimary,
            secondary = PinkSecondary,
            tertiary = PinkTertiary,
            background = PinkDarkBackground,
            surface = PinkDarkSurface,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFFFFE4E6),
            onSurface = Color(0xFFFFF1F2)
        )
        ThemePalette.Amber -> darkColorScheme(
            primary = AmberPrimary,
            secondary = AmberSecondary,
            tertiary = AmberTertiary,
            background = AmberDarkBackground,
            surface = AmberDarkSurface,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFFFEF3C7),
            onSurface = Color(0xFFFFFBEB)
        )
    }
}

private fun getLightColorScheme(palette: ThemePalette): ColorScheme {
    return when (palette) {
        ThemePalette.Indigo -> lightColorScheme(
            primary = IndigoPrimary,
            secondary = IndigoSecondary,
            tertiary = IndigoTertiary,
            background = IndigoLightBackground,
            surface = IndigoLightSurface,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF1E293B),
            onSurface = Color(0xFF0F172A)
        )
        ThemePalette.Teal -> lightColorScheme(
            primary = TealPrimary,
            secondary = TealSecondary,
            tertiary = TealTertiary,
            background = TealLightBackground,
            surface = TealLightSurface,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF115E59),
            onSurface = Color(0xFF042F2E)
        )
        ThemePalette.Pink -> lightColorScheme(
            primary = PinkPrimary,
            secondary = PinkSecondary,
            tertiary = PinkTertiary,
            background = PinkLightBackground,
            surface = PinkLightSurface,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF9F1239),
            onSurface = Color(0xFF4C0519)
        )
        ThemePalette.Amber -> lightColorScheme(
            primary = AmberPrimary,
            secondary = AmberSecondary,
            tertiary = AmberTertiary,
            background = AmberLightBackground,
            surface = AmberLightSurface,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF92400E),
            onSurface = Color(0xFF451A03)
        )
    }
}

@Composable
fun AndroidBasicsTutorialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    palette: ThemePalette = ThemePalette.Indigo,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) getDarkColorScheme(palette) else getLightColorScheme(palette)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
