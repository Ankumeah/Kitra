package com.ankumeah.github.kitra.viewModels

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ankumeah.github.kitra.apiCalls.BackendRetrofitInstance
import com.ankumeah.github.kitra.classes.Kitra
import com.ankumeah.github.kitra.models.Contact
import com.ankumeah.github.kitra.models.Misc
import com.ankumeah.github.kitra.models.RefreshToken
import com.ankumeah.github.kitra.models.SessionToken
import com.ankumeah.github.kitra.models.User
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataBaseViewModel: ViewModel() {
  private val realm = Kitra.realm

  val contacts = realm.query<Contact>().asFlow()
    .map { it.list.toList() }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

  val user = realm.query<User>().first().asFlow().map { it.obj }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), User())

  val misc = realm.query<Misc>().first().asFlow().map { it.obj }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Misc())

  val refreshToken = realm.query<RefreshToken>().first().asFlow().map { it.obj }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), RefreshToken())

  val sessionToken = realm.query<SessionToken>().first().asFlow().map { it.obj }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SessionToken())

  init {
    viewModelScope.launch {
      realm.write {
        val existing = this.query<Misc>().first().find()
        if (existing == null) {
          copyToRealm(
            Misc().apply {
              isDarkTheme = true
            }
          )
        }
      }
    }
  }

  fun logIn(user: String, mail: String, refreshToken: String, refreshTokenExpiry: Long, sessionToken: String, sessionTokenExpiry: Long) {
    viewModelScope.launch {
      realm.write {
        val user = User().apply {
          username = user
          email = mail
        }
        val refreshToken = RefreshToken().apply {
          value = refreshToken
          expiry = refreshTokenExpiry
        }
        val sessionToken = SessionToken().apply {
          value = sessionToken
          expiry = sessionTokenExpiry
        }

        copyToRealm(user, updatePolicy = UpdatePolicy.ALL)
        copyToRealm(refreshToken, updatePolicy = UpdatePolicy.ALL)
        copyToRealm(sessionToken, updatePolicy = UpdatePolicy.ALL)
      }
    }
  }

  fun logOut(navController: NavController) {
    viewModelScope.launch {
      realm.write {
        delete(query<User>())
        delete(query<Contact>())
      }
    }
    CoroutineScope(Dispatchers.IO).launch {
      try {
        val body = mapOf(
          "email" to user.value?.email.toString(),
          "refresh_token" to refreshToken.value?.value.toString()
        )

        BackendRetrofitInstance.logoutApi.logout(body)
        withContext(Dispatchers.Main) {
          val entry = navController.currentBackStackEntry
          val dest = entry?.destination?.route
          val args = entry?.arguments
          val evaluatedRoute = dest?.let { pattern ->
            pattern.replace(Regex("\\{(.+?)\\}")) { match ->
              val key = match.groupValues[1]
              args?.get(key)?.toString() ?: match.value
            }
          }

          navController.navigate(("SignInPage")) { popUpTo(evaluatedRoute ?: "SettingScreen") { inclusive = true } }
        }
      }
      catch (e: Exception) {
        withContext(Dispatchers.Main) {
          Log.e("DataBaseViewModel", "An error occurred: $e")
        }
      }
    }
  }

  suspend fun renewSessionToken(): Int {
    return withContext(Dispatchers.IO) {
      try {
        val body = mapOf(
          "email" to user.value?.email.toString(),
          "session_token" to sessionToken.value?.value.toString()
        )

        val res = BackendRetrofitInstance.renewSessionTokenApi.renewSessionToken(body)

        if (res.isSuccessful) {
          withContext(Dispatchers.Main) {
            realm.write {
              val sessionToken = SessionToken().apply {
                value = res.body()!!.session_token
                expiry = res.body()!!.session_token_expire
              }
            }
          }
        } else {
          Log.e("DataBaseViewModel", "res = ${res.code()}, ${res.message()}")
          throw Exception(res.code().toString())
        }
      } catch (e: Exception) {
        if (e.message?.isDigitsOnly() == true) {
        return@withContext e.message!!.toInt()
        }
        else {
          Log.e("DataBaseViewModel", "${e.cause}: ${e.message}")
          return@withContext 1
        }
      }

      return@withContext 0
    }
  }

  suspend fun isValidUser(email: String): Int {
    return withContext(Dispatchers.IO) {
      try {
        val res = BackendRetrofitInstance.isValidUserApi.isValidUser(mapOf("email" to email))

        if (res.isSuccessful) {
          if (res.body()?.message == "true") { return@withContext 0 }
          else { return@withContext 1 }
        }
        else {
          Log.e("DataBaseViewModel", "res = ${res.code()}, ${res.message()}")
          throw Exception(res.code().toString())
        }
      }
      catch (e: Exception) {
        if (e.message?.isDigitsOnly() == true) {
          return@withContext e.message!!.toInt()
        }
        else {
          Log.e("DataBaseViewModel", "${e.cause}: ${e.message}")
          return@withContext -1
        }
      }
    }
  }

  fun deleteContact(contact: Contact) {
    viewModelScope.launch {
      realm.write { delete(query<Contact>("_id == $0", contact._id)) }
    }
  }

  fun updateMisc(isDarkTheme: Boolean) {
    viewModelScope.launch {
      realm.write {
        val misc = Misc().apply {
          this.isDarkTheme = isDarkTheme
        }
        copyToRealm(misc, UpdatePolicy.ALL)
      }
    }
  }

  fun addContact(contact: Contact) {
    viewModelScope.launch {
      realm.write { copyToRealm(contact, UpdatePolicy.ALL) }
    }
  }

  fun findContact(contactName: String): Contact? {
    return realm.query<Contact>("contactName == $0", contactName).first().find()
  }
}
