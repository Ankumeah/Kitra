package com.ankumeah.github.kitra.classes

import android.app.Application
import com.ankumeah.github.kitra.models.Contact
import com.ankumeah.github.kitra.models.Message
import com.ankumeah.github.kitra.models.Misc
import com.ankumeah.github.kitra.models.User
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class Kitra: Application() {
  companion object {
    lateinit var realm: Realm
  }

  override fun onCreate() {
    super.onCreate()
    realm = Realm.open(
      configuration = RealmConfiguration.create(
        schema = setOf(
          Contact::class,
          Message::class,
          User::class,
          Misc::class
        )
      )
    )
  }
}