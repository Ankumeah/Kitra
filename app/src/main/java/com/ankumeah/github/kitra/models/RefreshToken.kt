package com.ankumeah.github.kitra.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RefreshToken(): RealmObject {
  @PrimaryKey var _id: String = "refreshToken"
  var value: String = ""
  var expiry: Long = -1
}

