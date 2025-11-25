package com.ankumeah.github.kitra

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ankumeah.github.kitra.auth.GoogleSignInScreen
import com.ankumeah.github.kitra.screens.ChatScreen
import com.ankumeah.github.kitra.screens.MainScreen
import com.ankumeah.github.kitra.screens.SettingsScreen
import com.ankumeah.github.kitra.screens.SignInPage
import com.ankumeah.github.kitra.ui.theme.KitraTheme
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.DataBaseViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
  val dataBaseViewModel: DataBaseViewModel by viewModels()

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      KitraTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          val user = dataBaseViewModel.user.collectAsStateWithLifecycle()
          val misc = dataBaseViewModel.misc.collectAsStateWithLifecycle()
          val colors = remember(misc.value?.isDarkTheme) {
            ColorsViewModel(misc.value?.isDarkTheme != false)
          }

          val startScreen = if (user.value?.username != null) "MainScreen" else "SignInPage"

          App(
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
fun App(modifier: Modifier = Modifier, startScreen: String, dataBaseViewModel: DataBaseViewModel, colors: ColorsViewModel) {
  val navController = rememberNavController()

  NavHost(navController = navController, startDestination = startScreen) {
    composable("MainScreen") { MainScreen(modifier = modifier.fillMaxSize(), navController = navController, colors = colors, dataBaseViewModel = dataBaseViewModel) }
    composable("SettingsScreen") { SettingsScreen(modifier = modifier.fillMaxSize(), navController = navController, dataBaseViewModel = dataBaseViewModel, colors = colors) }
    composable("SignInPage") { SignInPage(modifier = modifier.fillMaxSize(), navController = navController, colors = colors) }
    composable("auth/GoogleSignIn") { GoogleSignInScreen(modifier = modifier.fillMaxSize(), navController = navController, colors = colors) }
    composable("chats/{contact}") { backStackEntry ->
      val contact = dataBaseViewModel.findContact(backStackEntry.arguments?.getString("contact").toString())
      if (contact != null) {
        ChatScreen(modifier = modifier.fillMaxSize(), navController = navController, colors = colors, contact = contact)
      }
    }
  }

    val refreshToken = dataBaseViewModel.refreshToken.collectAsStateWithLifecycle()
    val sessionToken = dataBaseViewModel.sessionToken.collectAsStateWithLifecycle()
    val context: Context = LocalContext.current

  LaunchedEffect("TokenChecker") {
    while (true) {
      Log.i("App", "Starting TokenChecker")
      val lastCheck: Long = System.currentTimeMillis() / 1000

      if (refreshToken.value != null && refreshToken.value?.value != "" && refreshToken.value!!.expiry <= lastCheck) {
        Toast.makeText(context, "Your session timed out, please log in again", Toast.LENGTH_LONG).show()
        dataBaseViewModel.logOut(navController = navController)
      }

      if (sessionToken.value != null && sessionToken.value?.value != "" && sessionToken.value!!.expiry <= lastCheck + 60 * 10) {
        var tryCount = 1
        val maxTries = 3

        while (tryCount <= 3) {
          val res: Int = dataBaseViewModel.renewSessionToken()
          if (res == 0) {
            Log.i("App", "Session token renewed")
            break
          } else if (res == 401) {
            Log.i("App", "Session token expired")

            dataBaseViewModel.logOut(navController = navController)
            break
          } else {
            tryCount++
            if (tryCount < maxTries) {
              Log.i("App", "Session token renewal attempt $tryCount failed, trying again")
            } else {
              Log.e("App", "Session token renewal failed, max tries reached")
            }
          }
        }
      }

      delay(1000 * 5 * 60)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
  KitraTheme {
    val colorsViewModel = ColorsViewModel()
    App(modifier = Modifier.fillMaxSize(), startScreen = "MainScreen", dataBaseViewModel = DataBaseViewModel(), colors = colorsViewModel)
  }
}