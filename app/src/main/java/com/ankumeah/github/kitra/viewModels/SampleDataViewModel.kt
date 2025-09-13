package com.ankumeah.github.kitra.viewModels

import androidx.lifecycle.ViewModel
import com.ankumeah.github.kitra.models.Contact
import com.ankumeah.github.kitra.models.Misc
import com.ankumeah.github.kitra.models.User

class SampleDataViewModel: ViewModel() {
  val contactList: List<Contact> = listOf(
    Contact().apply {
      contactName = "Stuff"
      contactAddress = "he@tho"
    },
    Contact().apply {
      contactName = "Stuff"
      contactAddress = "he@tho"
    },
    Contact().apply {
      contactName = "Stuff"
      contactAddress = "he@tho"
    },
    Contact().apply {
      contactName = "Stuff"
      contactAddress = "he@tho"
    }
  )
  val text: String = "Smt Smt Text"
  val textLong: String = "Big sample text to be used at the time of dev"
  val misc: List<Misc> = listOf(Misc())
  val user: List<User> = listOf(User())
}