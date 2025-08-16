package com.ankumeah.github.kitra.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Misc: RealmObject {
  @PrimaryKey var _id: String = "misc"
  var isDarkTheme: Boolean = true
}