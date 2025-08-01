package com.ankumeah.github.kitra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ankumeah.github.kitra.screens.MainScreen
import com.ankumeah.github.kitra.ui.theme.KitraTheme
import com.ankumeah.github.kitra.viewModels.SampleDataViewModel
import com.ankumeah.github.kitra.screens.ChatScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KitraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainActivity(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainActivity(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val contacts = remember { SampleDataViewModel().contactList }

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") { MainScreen(modifier = modifier.fillMaxSize(), navController = navController) }
        for (contact in contacts) {
            composable(contact.contactName) { ChatScreen(modifier = modifier.fillMaxSize(), navController = navController, contact = contact) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    KitraTheme {
        MainActivity(modifier = Modifier.fillMaxSize())
    }
}