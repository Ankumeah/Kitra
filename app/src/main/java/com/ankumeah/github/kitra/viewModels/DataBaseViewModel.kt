package com.ankumeah.github.kitra.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankumeah.github.kitra.classes.Kitra
import com.ankumeah.github.kitra.models.Contact
import com.ankumeah.github.kitra.models.Misc
import com.ankumeah.github.kitra.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DataBaseViewModel: ViewModel() {
  private val realm = Kitra.realm

  val contacts = realm
    .query<Contact>()
    .asFlow()
    .map { results ->
      results.list.toList()
    }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(),
      emptyList()
    )

  val user = realm
    .query<User>()
    .asFlow()
    .map { results ->
      results.list.toList()
    }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(),
      emptyList()
    )

  val misc = realm
    .query<Misc>()
    .asFlow()
    .map { results ->
      results.list.toList()
    }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(),
      emptyList()
    )

  val colors = ColorsViewModel(misc.value.firstOrNull()?.isDarkTheme != false)

  fun getUserEmail() {
    viewModelScope.launch {
      realm.write {
        val user = User().apply {
          _id = "user"
          username = Firebase.auth.currentUser?.displayName
          email = Firebase.auth.currentUser?.email
        }

        copyToRealm(user, updatePolicy = UpdatePolicy.ALL)
      }
    }
  }

  fun removeUserEmail() {
    viewModelScope.launch {
      realm.write {
        val user = query<User>()
        delete(user)
      }
    }
  }

  fun updateMisc() {
    viewModelScope.launch {
      realm.write {
        val misc = Misc().apply {
          _id = "misc"
          isDarkTheme = colors.isDarkTheme()
        }

        copyToRealm(misc, updatePolicy = UpdatePolicy.ALL)
      }
    }
    if (colors.isDarkTheme() == this.misc.value.firstOrNull()?.isDarkTheme) Log.i("DataBaseViewModel", "Color swap passed") else Log.w("DataBaseViewModel", "Color swap failed")
  }
}