package com.ankumeah.github.kitra.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class SessionToken(): RealmObject {
  @PrimaryKey var _id: String = "sessionToken"
  var value: String = ""
  var expiry: Long = -1
}