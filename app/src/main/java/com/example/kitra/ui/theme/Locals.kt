package com.example.kitra.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("NavController not provided. Did you forget CompositionLocalProvider?")
}
