package com.example.kitra.composables.chatScreen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kitra.ui.theme.KitraTheme
import com.example.kitra.ui.theme.kitraColors
import java.nio.file.WatchEvent

@Composable
fun TextBubble(modifier: Modifier = Modifier, text: String, isSentByMe: Boolean = true) {
    Row(modifier = modifier.fillMaxWidth().background(kitraColors().background)) {
        if (isSentByMe) {
            Spacer(modifier = Modifier.weight(0.1f))
        }
        Row(modifier = Modifier.weight(0.9f), horizontalArrangement = if (isSentByMe) Arrangement.End else Arrangement.Start) {
            Text(
                text = text,
                color = kitraColors().text,
                modifier = Modifier.clip(RoundedCornerShape(5.dp))
                    .background(color = kitraColors().primary).padding(5.dp)
            )
        }
        if (!isSentByMe) {
            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun LightModeTextBubblePreview() {
    KitraTheme {
        TextBubble(text = "heaven is stupid :stupid", modifier = Modifier.fillMaxWidth())
    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DarkModeTextBubblePreview() {
    KitraTheme {
        TextBubble(text = "hell is fire :fire", modifier = Modifier.fillMaxWidth())
    }
}