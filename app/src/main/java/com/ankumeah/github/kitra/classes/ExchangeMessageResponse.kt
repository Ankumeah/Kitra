package com.ankumeah.github.kitra.classes

data class ExchangeMessageResponse(
    val content: String,
    val receiver_email: String,
    val session_token: String
)