package com.ankumeah.github.kitra.viewModels

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

  val contacts = realm.query<Contact>().asFlow()
    .map { it.list.toList() }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

  val user = realm.query<User>().asFlow()
    .map { it.list.toList() }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

  val misc = realm.query<Misc>().asFlow()
    .map { it.list.toList() }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

  init {
    viewModelScope.launch {
      realm.write {
        val existing = this.query<Misc>("_id == $0", "misc").first().find()
        if (existing == null) {
          copyToRealm(Misc().apply {
            _id = "misc"
            isDarkTheme = true
          })
        }
      }
    }
  }

  fun findContact(contactName: String) = realm.query<Contact>("contactName == $0", contactName).first().find()

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
      realm.write { delete(query<User>()) }
    }
  }

  fun updateMisc(isDarkTheme: Boolean) {
    viewModelScope.launch {
      realm.write {
        val misc = Misc().apply {
          _id = "misc"
          this.isDarkTheme = isDarkTheme
        }
        copyToRealm(misc, UpdatePolicy.ALL)
      }
    }
  }

  fun addContact(contact: Contact) {
    viewModelScope.launch {
      realm.write { copyToRealm(contact, UpdatePolicy.ERROR) }
    }
  }
}