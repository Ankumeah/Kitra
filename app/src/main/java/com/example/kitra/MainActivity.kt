package com.example.kitra

import android.os.Bundle
import android.os.Message
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kitra.functions.textMessage
import com.example.kitra.screens.ChatScreen
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

    val contacts = remember { mutableListOf("01234567890", "1", "2", "3", "4","5", "6", "7", "8", "9", "0", "some ahh number") }
    val chats = remember { mutableListOf(textMessage("u stupid", true), textMessage("u too", false), textMessage("ik", true)) }
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController)
    {
        NavHost(navController = navController, startDestination = "Home")
        {
            composable("Home") { MainScreen(modifier = modifier, contacts = contacts) }
            for (contact in contacts) {
                composable(contact) { ChatScreen(modifier = modifier, contactName = contact, chats = chats) }
            }
        }
    }
}