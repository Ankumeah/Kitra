package com.ankumeah.github.kitra.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


class ColorsViewModel(isDarkMode: Boolean = true) {
  private var isDark by mutableStateOf(isDarkMode)

  fun swapTheme(isDark: Boolean? = null) {
    this.isDark = isDark ?: !this.isDark
  }

  fun isDarkTheme(): Boolean = isDark

  private fun makeColor(darkColor: Color, lightColor: Color, isDark: Boolean? = null): Color = if (this.isDark || isDark == true) darkColor else lightColor

  fun primary(isDark: Boolean? = null): Color = makeColor(darkColor = Color.Gray, lightColor = Color.White, isDark = isDark)
  fun secondary(isDark: Boolean? = null): Color = makeColor(darkColor = Color.DarkGray, lightColor = Color.LightGray, isDark = isDark)
  fun text(isDark: Boolean? = null): Color = makeColor(darkColor = Color.White, lightColor = Color.Black, isDark = isDark)
}