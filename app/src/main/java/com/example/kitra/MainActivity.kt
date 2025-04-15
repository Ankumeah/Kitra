package com.example.kitra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kitra.functions.textMessage
import com.example.kitra.screens.ChatScreen
import com.example.kitra.screens.MainScreen
import com.example.kitra.ui.theme.KitraTheme

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

    val contacts = remember { mutableListOf("Stupid 1", "Stupid 2", "Stupid 3", "Stupid 4", "also Stupid 4","Stupid 5", "Stupid 6", "Stupid 7", "Stupid 8", "Stupid 9", "Stupid 0", "Stupid some ahh number") }
    val chats = remember { mutableListOf(textMessage("u stupid", true), textMessage("u too", false), textMessage("ik", true)) }
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Home")
    {
        composable("Home") { MainScreen(modifier = modifier, contacts = contacts, navController = navController) }

        for (contact in contacts) {
            composable(contact) { ChatScreen(modifier = modifier, contactName = contact, chats = chats, navController = navController) }
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkPreview() {
    KitraTheme {
        AppNavHost()
    }
}
@Preview(name = "Light Mode", showBackground = true)
@Composable
fun LightPreview() {
    KitraTheme {
        AppNavHost()
    }
}
