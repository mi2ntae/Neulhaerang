package com.finale.neulhaerang.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
//    onPrimary = OnPrimary,
//    primaryContainer = PrimaryContainer,
//    onPrimaryContainer = OnPrimaryContainer,
//    inversePrimary = InversePrimary,
    secondary = PurpleGrey40,
//    onSecondary = OnSecondary,
//    secondaryContainer = SecondaryContainer,
//    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Pink40,
//    onTertiary = OnTertiary,
//    tertiaryContainer = TertiaryContainer,
//    onTertiaryContainer = OnTertiaryContainer,
//    background = Background,
    onBackground = TextColor,
//    surface = Surface,
    onSurface = TextColor,
//    surfaceVariant = SurfaceVariant,
//    onSurfaceVariant = OnSurfaceVariant,
//    surfaceTint = primary,
//    inverseSurface = InverseSurface,
//    inverseOnSurface = InverseOnSurface,
//    error = Error,
//    onError = OnError,
//    errorContainer = ErrorContainer,
//    onErrorContainer = OnErrorContainer,
//    outline = Outline,
//    outlineVariant = OutlineVariant,
//    scrim = Scrim,
)

@Composable
fun NeulHaeRangTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
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
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}