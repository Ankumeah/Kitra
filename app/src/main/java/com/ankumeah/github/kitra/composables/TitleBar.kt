package com.ankumeah.github.kitra.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ankumeah.github.kitra.Branding
import com.ankumeah.github.kitra.viewModels.ColorsViewModel

@Composable
fun TitleBar(modifier: Modifier = Modifier, colors: ColorsViewModel) {
  val branding: Branding = viewModel()

  Box(
    contentAlignment = Alignment.CenterStart,
    modifier = modifier
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
}