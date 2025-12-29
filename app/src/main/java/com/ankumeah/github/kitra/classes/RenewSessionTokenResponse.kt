package com.ankumeah.github.kitra.classes

data class RenewSessionTokenResponse(
    val session_token: String,
    val session_token_expire: Long
)