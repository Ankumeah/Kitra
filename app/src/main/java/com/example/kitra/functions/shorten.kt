package com.example.kitra.functions

fun shorten(text: String, maxLength: Int = 10): String {
    return if (text.length > maxLength) {
        text.take(maxLength) + "…"
    } else {
        text
    }
}