package com.ankumeah.github.kitra.auth

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ankumeah.github.kitra.BuildConfig
import com.ankumeah.github.kitra.composables.TitleBar
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.DataBaseViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

@Composable
fun GoogleSignInScreen(context: Context, navController: NavController, modifier: Modifier = Modifier, colors: ColorsViewModel) {
  val webClientId = BuildConfig.webClientId
  val dataBaseViewModel: DataBaseViewModel = viewModel()

  Column(modifier = modifier.background(colors.primary())) {
    TitleBar(modifier = Modifier.fillMaxWidth().weight(0.1f), colors = colors)
    Box(modifier = Modifier.fillMaxWidth().weight(0.9f), contentAlignment = Alignment.Center) {
      Text(text = "Waiting for Sign In", fontSize = 24.sp, color = colors.text())
    }
  }

  val googleSignInOptions = remember {
    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(webClientId)
      .requestEmail()
      .build()
  }

  val googleSignInClient = remember {
    GoogleSignIn.getClient(context, googleSignInOptions)
  }

  val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
    try {
      val account = task.result
      val credential = GoogleAuthProvider.getCredential(account.idToken, null)
      Firebase.auth.signInWithCredential(credential)
        .addOnCompleteListener {
          if (task.isSuccessful) {
            Toast.makeText(context, "SignIn Successful", Toast.LENGTH_SHORT).show()
            navController.navigate("MainScreen") {
              popUpTo("auth/GoogleSignIn") { inclusive = true }
            }
            dataBaseViewModel.getUserEmail()
          }
          else {
            Toast.makeText(context, "SignIn Failed", Toast.LENGTH_LONG).show()
            navController.navigate("SignInPage") {
              popUpTo("auth/GoogleSignIn") { inclusive = true }
            }
          }
        }
    }
    catch(e: Exception) {
      Toast.makeText(context, "Error, check logs for details", Toast.LENGTH_SHORT).show()
      println("Error while signing in with google: ${e.message}")
      navController.navigate("SignInPage") {
        popUpTo("auth/GoogleSignIn") { inclusive = true }
      }
    }
  }

  LaunchedEffect(Unit) {
    val signInIntent = googleSignInClient.signInIntent
    launcher.launch(signInIntent)
  }
}