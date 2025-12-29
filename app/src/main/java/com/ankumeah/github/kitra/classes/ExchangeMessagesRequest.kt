package com.ankumeah.github.kitra.classes

data class ExchangeMessagesRequest(
    val content: String,
    val sender_email: String,
    val unix_time: Long
)