package com.example.kitra.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kitra.functions.shorten
import com.example.kitra.ui.theme.LocalNavController
import com.example.kitra.ui.theme.kitraColors

@Composable
fun ChatScreenTitleBar(contactName: String, modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    Row(modifier = modifier) {
        Text(
            text = "K",
            color = kitraColors().text,
            fontSize = 70.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(0.2f)
                .fillMaxSize()
                .clip(RoundedCornerShape(40.dp))
                .clickable{navController.navigate("Home")}
        )
        Box(modifier = Modifier.weight(0.8f).fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = shorten(contactName),
                color = kitraColors().text,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}