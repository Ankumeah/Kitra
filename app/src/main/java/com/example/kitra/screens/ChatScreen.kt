package com.example.kitra.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kitra.composables.chatScreen.ChatScreenTitleBar
import com.example.kitra.composables.chatScreen.SendMessageBar
import com.example.kitra.composables.chatScreen.TextBubble
import com.example.kitra.functions.textMessage
import com.example.kitra.ui.theme.KitraTheme
import com.example.kitra.ui.theme.kitraColors

@Composable
fun ChatScreen(contactName: String, modifier: Modifier = Modifier, chats: List<textMessage> = emptyList(), navController: NavHostController) {
    Column(modifier = modifier
        .fillMaxSize()
        .background(kitraColors().background)
    ) {
        ChatScreenTitleBar(contactName = contactName, modifier = Modifier.weight(0.1f).fillMaxSize().shadow(3.dp), navController = navController)
        LazyColumn(modifier = Modifier.weight(0.8f).fillMaxSize()) {
            items(chats.size) { index ->
                if (chats[index].text != "") {
                    TextBubble(
                        text = chats[index].text,
                        modifier = Modifier
                            .padding(5.dp),
                        isSentByMe = chats[index].isSentByMe
                    )
                }
                else { Unit }
            }
        }
        SendMessageBar(modifier = Modifier.padding(bottom = 5.dp, start = 10.dp, end = 10.dp))
    }
}

@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkPreview() {
    KitraTheme {
        val navController = rememberNavController()
        ChatScreen(contactName = "Stupid", navController = navController, chats = listOf(
            textMessage("yo", false),
            textMessage("sup", true)
        ))
    }
}
@Preview(name = "Light Mode", showBackground = true)
@Composable
fun LightPreview() {
    KitraTheme {
        val navController = rememberNavController()
        ChatScreen(contactName = "Stupid", navController = navController, chats = listOf(
            textMessage("yo", false),
            textMessage("sup", true)
        ))
    }
}