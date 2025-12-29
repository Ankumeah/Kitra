package com.ankumeah.github.kitra.classes

data class LoginResponse(
  val refresh_token: String,
  val refresh_token_expire: Long,
  val session_token: String,
  val session_token_exire: Long
)