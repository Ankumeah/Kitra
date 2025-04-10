package com.example.kitra.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kitra.ui.theme.KitraTheme
import com.example.kitra.ui.theme.kitraColors
import com.example.kitra.composables.ChatScreenTitleBar
import com.example.kitra.composables.TextBubble
import com.example.kitra.functions.textMessage

@Composable
fun ChatScreen(contactName: String, modifier: Modifier = Modifier, chats: List<textMessage> = emptyList()) {
    Column(modifier = modifier.fillMaxSize().background(kitraColors().background)) {
        ChatScreenTitleBar(contactName = contactName, modifier = Modifier.weight(0.1f).fillMaxSize().shadow(3.dp).padding(top = 5.dp, end = 10.dp, start = 5.dp))
        LazyColumn(modifier = Modifier.weight(0.9f).fillMaxSize()) {
            items(chats.size) { index ->
                val message = chats[index]
                TextBubble(
                    text = message.text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    isSentByMe = message.isSentByMe
                )
            }
        }
    }
}