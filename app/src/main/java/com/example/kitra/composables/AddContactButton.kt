package com.example.kitra.composables

import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kitra.AppNavHost
import com.example.kitra.ui.theme.KitraTheme
import com.example.kitra.ui.theme.kitraColors

@Composable
fun AddContactButton(modifier: Modifier = Modifier, fontSize: TextUnit = 24.sp) {
    val isDisplayed = remember { mutableStateOf(false) }
    Box(modifier = modifier.clip(RoundedCornerShape(40.dp)).background(kitraColors().primary).clickable{ isDisplayed.value = true }, contentAlignment = Alignment.Center) {
        Text(
            text = "+",
            color = kitraColors().text,
            fontSize = fontSize
        )
    }
    if (isDisplayed.value) {
        AddContactAlert(isDisplayed = isDisplayed)
    }
}