package com.lms.weatherapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = primary,
    secondary = primaryDark,
    primaryVariant = accent
)

private val LightColorPalette = lightColors(
    primary = primary,
    secondary = primaryDark,
    primaryVariant = accent
)

@Composable
fun WeatherComposeTheme(
    darkTheme: Boolean = false,
    content: @Composable() () -> Unit)
{
        val colors = if(darkTheme){
            DarkColorPalette
        } else {
            LightColorPalette
        }

        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
