package com.example.kitra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kitra.screens.MainScreen
import com.example.kitra.ui.theme.KitraTheme
import com.example.kitra.ui.theme.LocalNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KitraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(modifier = Modifier.fillMaxSize().padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    androidx.compose.runtime.CompositionLocalProvider(
        LocalNavController provides navController
    )
    {
        NavHost(navController = navController, startDestination = "Home")
        {
            composable("Home") { MainScreen(modifier = modifier, contacts = listOf("01234567890", "1", "2", "3", "4","5", "6", "7", "8", "9", "0", "some ahh number")) }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun LightModePreview() {
    KitraTheme {
        AppNavHost()
    }
}

@Preview(name = "Dark Mode", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DarkModePreview() {
    KitraTheme {
        AppNavHost()
    }
}
