package com.ankumeah.github.kitra.auth

import android.content.Context
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ankumeah.github.kitra.BuildConfig
import com.ankumeah.github.kitra.apiCalls.BackendRetrofitInstance
import com.ankumeah.github.kitra.composables.TitleBar
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.DataBaseViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun GoogleSignInScreen(navController: NavController, modifier: Modifier = Modifier, colors: ColorsViewModel, dataBaseViewModel: DataBaseViewModel) {
  val webClientId = BuildConfig.webClientId
  val dataBaseViewModel: DataBaseViewModel = viewModel()
  val context: Context = LocalContext.current

  Column(modifier = modifier.background(colors.primary())) {
    TitleBar(modifier = Modifier.fillMaxWidth().weight(0.1f), colors = colors)
    Box(modifier = Modifier.fillMaxWidth().weight(0.9f), contentAlignment = Alignment.Center) {
      Text(text = "Waiting for Sign In", fontSize = 24.sp, color = colors.text())
    }
  }

  val request = GetCredentialRequest(
    listOf(
      GetGoogleIdOption.Builder()
        .setServerClientId(webClientId)
        .setFilterByAuthorizedAccounts(false)
        .build()
    )
  )

  LaunchedEffect(Unit) {
    val credentialManager = CredentialManager.create(context)
    val result = credentialManager.getCredential(context, request)
    val google = result.credential as GoogleIdTokenCredential
    val token = google.idToken

    val tokenParts: List<String> = token.split(".")
    val body = String(Base64.decode(tokenParts[1], Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP))
    val mapType = object : TypeToken<Map<String, String>>() {}.type

    val info: Map<String, String> = Gson().fromJson(body, mapType)
    val email = info["email"] ?: ""

    if (email == "") {
      Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
      navController.navigate("SignInPage") { popUpTo("auth/GoogleSignIn") { inclusive = true } }
    }
    else {
      CoroutineScope(Dispatchers.IO).launch {
        try {
          val res = BackendRetrofitInstance.loginApi.sendLoginInfo(mapOf("JWT_token" to token))

          withContext(Dispatchers.Main) {
            if (res.isSuccessful) {
              Toast.makeText(context, "Logged in as $email", Toast.LENGTH_SHORT).show()
              dataBaseViewModel.logIn(
                mail = email,
                refreshToken = res.body()!!.refresh_token,
                refreshTokenExpiry = res.body()!!.refresh_token_expire,
                sessionToken = res.body()!!.session_token,
                sessionTokenExpiry = res.body()!!.session_token_exire
              )
              navController.navigate(("MainScreen")) { popUpTo("auth/GoogleSignIn") { inclusive = true } }
            }
            else {
              Log.e("GoogleSignInScreen", res.body().toString())
              navController.navigate("SignInPage") { popUpTo("auth/GoogleSignIn") { inclusive = true } }
            }
          }
        }
        catch (e: Exception) {
          withContext(Dispatchers.Main) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            navController.navigate("SignInPage") { popUpTo("auth/GoogleSignIn") { inclusive = true } }
            Log.e("GoogleSignInScreen", "${e.cause}: ${e.message}")
          }
        }
      }
    }
  }
}