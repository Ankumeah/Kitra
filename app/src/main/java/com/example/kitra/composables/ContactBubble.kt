package com.example.kitra.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kitra.ui.theme.LocalNavController
import com.example.kitra.ui.theme.kitraColors
import com.example.kitra.functions.shorten

@Composable
fun ContactBubble(modifier: Modifier = Modifier, contactName: String) {
    val navController = LocalNavController.current

    Row(modifier = modifier.clip(RoundedCornerShape(40.dp)).background(kitraColors().primary).clickable{navController.navigate(contactName)}) {
        Box(modifier = Modifier.width(100.dp).clip(RoundedCornerShape(40.dp)).fillMaxSize()) {
            Text("", color = Color.Red, fontSize = 100.sp, modifier = Modifier.fillMaxSize().padding(10.dp).background(color = Color.Red, shape = RoundedCornerShape(40.dp)))
        }
        Row(modifier = Modifier.weight(1f).fillMaxSize().padding(start = 10.dp, end = 25.dp)) {
            Box(modifier = Modifier.weight(0.75f).fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                Text(shorten(contactName), color = kitraColors().text, fontSize = 28.sp)
            }
        }
    }
}