package com.example.kitra.composables.chatScreen

import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kitra.ui.theme.KitraTheme
import com.example.kitra.ui.theme.kitraColors

@Composable
fun SendMessageBar(modifier: Modifier = Modifier) {
    val message = remember { mutableStateOf("") }

    Row(modifier = modifier.clip(RoundedCornerShape(20.dp)).background(kitraColors().primary), verticalAlignment = Alignment.Bottom) {
        TextField(
            modifier = Modifier.padding(start = 10.dp).weight(1f),
            value = message.value,
            maxLines = 5,
            onValueChange = { message.value = it },
            placeholder = { Text(text = "Send Message", color = kitraColors().text.copy(alpha = 0.4f)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = kitraColors().primary,
                unfocusedContainerColor = kitraColors().primary,
                disabledContainerColor = kitraColors().primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = kitraColors().text,
                focusedTextColor = kitraColors().text,
                unfocusedTextColor = kitraColors().text,
            ),
        )
        Box(
            modifier = Modifier
                .width(55.dp)
                .height(55.dp)
                .background(kitraColors().primary)
                .padding(5.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(kitraColors().background)
                .clickable {
                    if (message.value.isNotBlank()) {
                        println("Message Sent: ${message.value}")
                        message.value = ""
                    }
                    else { Unit }
                },
            contentAlignment = Alignment.Center
            ) {
                Text(text = "=>", color = kitraColors().primary, modifier = Modifier)
            }
    }
}

@Preview(name = "Dark Mode", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkPreview() {
    KitraTheme {
        SendMessageBar()
    }
}
@Preview(name = "Light Mode", showBackground = true)
@Composable
fun LightPreview() {
    KitraTheme {
        SendMessageBar()
    }
}
