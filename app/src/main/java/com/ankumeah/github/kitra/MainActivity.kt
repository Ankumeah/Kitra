package com.ankumeah.github.kitra

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ankumeah.github.kitra.auth.GoogleSignInScreen
import com.ankumeah.github.kitra.models.Contact
import com.ankumeah.github.kitra.screens.ChatScreen
import com.ankumeah.github.kitra.screens.MainScreen
import com.ankumeah.github.kitra.screens.SettingsScreen
import com.ankumeah.github.kitra.screens.SignInPage
import com.ankumeah.github.kitra.ui.theme.KitraTheme
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.DataBaseViewModel
import com.ankumeah.github.kitra.viewModels.SampleDataViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
  val dataBaseViewModel: DataBaseViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      KitraTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          val contacts = dataBaseViewModel.contacts.collectAsStateWithLifecycle()
          val misc = dataBaseViewModel.misc.collectAsStateWithLifecycle()
          val colors = remember(misc.value.firstOrNull()?.isDarkTheme) {
            ColorsViewModel(misc.value.firstOrNull()?.isDarkTheme != false)
          }

          val startScreen = if (Firebase.auth.currentUser != null) "MainScreen" else "SignInPage"

          MainActivity(
            contacts = contacts.value,
            startScreen = startScreen,
            dataBaseViewModel = dataBaseViewModel,
            colors = colors,
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun MainActivity(modifier: Modifier = Modifier, contacts: List<Contact>, startScreen: String, dataBaseViewModel: DataBaseViewModel, colors: ColorsViewModel) {
  val navController = rememberNavController()
  val context = LocalContext.current

  NavHost(navController = navController, startDestination = startScreen) {
    composable("MainScreen") { MainScreen(modifier = modifier.fillMaxSize(), navController = navController, contacts = contacts, colors = colors, dataBaseViewModel = dataBaseViewModel) }
    composable("SettingsScreen") { SettingsScreen(modifier = modifier.fillMaxSize(), navController = navController, dataBaseViewModel = dataBaseViewModel, colors = colors) }
    composable("SignInPage") { SignInPage(modifier = modifier.fillMaxSize(), navController = navController, colors = colors) }
    composable("auth/GoogleSignIn") { GoogleSignInScreen(modifier = modifier.fillMaxSize(), context = context, navController = navController, colors = colors) }
    composable("chats/{contact}") { backStackEntry ->
      val contact = dataBaseViewModel.findContact(backStackEntry.arguments?.getString("contact").toString())
      if (contact != null) {
        ChatScreen(
          modifier = modifier.fillMaxSize(),
          navController = navController,
          colors = colors,
          contact = contact
        )
      }
    }
  }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
  KitraTheme {
    MainActivity(modifier = Modifier.fillMaxSize(), contacts = SampleDataViewModel().contactList, startScreen = "MainScreen", dataBaseViewModel = DataBaseViewModel(), colors = ColorsViewModel())
  }
}