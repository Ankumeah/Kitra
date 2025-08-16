package com.ankumeah.github.kitra.auth

import android.content.Context
import com.ankumeah.github.kitra.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

fun googleSignOut(context: Context, onComplete: () -> Unit = {}) {
  val webClientId = BuildConfig.webClientId

  Firebase.auth.signOut()

  val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestIdToken(webClientId)
    .requestEmail()
    .build()

  val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
  googleSignInClient.signOut().addOnCompleteListener { onComplete() }
}