package com.ankumeah.github.kitra.models

import io.realm.kotlin.types.EmbeddedRealmObject

class Message: EmbeddedRealmObject {
  var text: String = ""
}