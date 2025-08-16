package com.ankumeah.github.kitra.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Contact: RealmObject {
  @PrimaryKey var _id: ObjectId = ObjectId()
  var contactName: String = ""
  var messages: RealmList<Message> = realmListOf()
}