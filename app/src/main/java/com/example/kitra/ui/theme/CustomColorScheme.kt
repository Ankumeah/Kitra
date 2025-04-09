package com.example.kitra.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme

data class KitraColorPalette(
    val background: Color,
    val primary: Color,
    val secondary: Color,
    val text: Color,
    val highlightedText: Color
)

@Composable
fun kitraColors(): KitraColorPalette {
    val dark = isSystemInDarkTheme()

    return if (dark) {
        KitraColorPalette(
            background = Color(23, 24, 26),
            primary = Color.DarkGray,
            secondary = Color(103, 177, 95),
            text = Color.White,
            highlightedText = Color.Green,
        )
    } else {
        KitraColorPalette(
            background = Color(0xFFFFFFFF),
            primary = Color.LightGray,
            secondary = Color.Cyan,
            text = Color.Black,
            highlightedText = Color.Blue,
        )
    }
}