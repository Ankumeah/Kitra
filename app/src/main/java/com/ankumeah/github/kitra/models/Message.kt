package com.ankumeah.github.kitra.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Message: RealmObject {
  @PrimaryKey var _id: ObjectId = ObjectId()
  var text: String = ""
  var timestamp: Long = 0
  var sentByUser: Boolean = false
}