package com.example.kitra.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kitra.ui.theme.LocalNavController
import com.example.kitra.ui.theme.kitraColors
import com.example.kitra.functions.shorten

@Composable
fun ContactBubble(modifier: Modifier = Modifier, contactName: String) {
    val navController = LocalNavController.current
    val showContextMenu = remember { mutableStateOf(false) }
    val pressOffset = remember { mutableStateOf(DpOffset.Zero) }
    var contactHeight by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current

    Row(modifier = modifier
        .onSizeChanged {
            contactHeight = with(density) { it.height.toDp() }
        }
        .clip(RoundedCornerShape(40.dp))
        .background(kitraColors().primary)
        .pointerInput(Unit) {
            detectTapGestures(
                onLongPress = {
                    pressOffset.value = DpOffset(x = it.x.toDp(), y = it.y.toDp())
                    showContextMenu.value = true },
                onTap = { navController.navigate(contactName) }
            )
        }) {
        Box(modifier = Modifier.width(100.dp).clip(RoundedCornerShape(40.dp)).fillMaxSize()) {
            Text("", color = Color.Red, fontSize = 100.sp, modifier = Modifier.fillMaxSize().padding(10.dp).background(color = Color.Red, shape = RoundedCornerShape(40.dp)))
        }
        Row(modifier = Modifier.weight(1f).fillMaxSize().padding(start = 10.dp, end = 25.dp)) {
            Box(modifier = Modifier.weight(0.75f).fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                Text(shorten(contactName), color = kitraColors().text, fontSize = 28.sp)
            }
        }
        if (showContextMenu.value) {
            ContactContextMenu(contactName = contactName, expanded = showContextMenu, offset = with(density) { DpOffset(x = pressOffset.value.x, y = pressOffset.value.y - contactHeight) })
        }
    }
}