package com.example.kitra.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kitra.ui.theme.LocalNavController
import com.example.kitra.ui.theme.kitraColors

@Composable
fun TitleBar(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    Row() {
        Text(text = " Kitra ", color = kitraColors().text, fontSize = 40.sp, modifier = Modifier.clip(RoundedCornerShape(40.dp)).clickable{navController.navigate("Home")})
    }
}