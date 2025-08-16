package com.ankumeah.github.kitra.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class User: RealmObject {
  @PrimaryKey var _id: String = "user"
  var username: String? = ""
  var email: String? = ""
}