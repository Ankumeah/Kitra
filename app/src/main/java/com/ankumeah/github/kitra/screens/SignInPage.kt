package com.ankumeah.github.kitra.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ankumeah.github.kitra.ui.theme.KitraTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ankumeah.github.kitra.Branding
import com.ankumeah.github.kitra.composables.TitleBar
import com.ankumeah.github.kitra.viewModels.ColorsViewModel

@Composable
fun SignInPage(modifier: Modifier = Modifier) {
    val colors = remember { ColorsViewModel() }
    val branding = remember { Branding() }
    Column(modifier = modifier.background(colors.primary())) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.1f)
                .background(colors.secondary())
                .padding(10.dp)
        ) {
            Text(
                text = branding.appName,
                color = colors.text(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Sign In/Up",
                color = colors.text(),
                fontSize = 42.sp,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(15.dp))
                    .background(color = colors.secondary())
                    .padding(10.dp)
                    .clickable {
                        Unit
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPagePreview() {
    KitraTheme {
        SignInPage(modifier = Modifier.fillMaxSize())
    }
}
