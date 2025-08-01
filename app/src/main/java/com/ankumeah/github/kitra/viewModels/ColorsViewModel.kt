package com.ankumeah.github.kitra.viewModels

import androidx.compose.ui.graphics.Color

class ColorsViewModel {
    private var isDark: Boolean = true

    fun swapTheme() {
        isDark = ! isDark
    }

    private fun makeColor(darkColor: Color, lightColor: Color): Color {
        return if (isDark == true) {
            darkColor
        } else {
            lightColor
        }
    }

    fun primary(): Color {
        return makeColor(darkColor = Color.Gray, lightColor = Color.White)
    }
    fun secondary(): Color {
        return makeColor(darkColor = Color.DarkGray, lightColor = Color.LightGray)
    }
    fun text(): Color {
        return makeColor(darkColor = Color.White, lightColor = Color.Black)
    }
}