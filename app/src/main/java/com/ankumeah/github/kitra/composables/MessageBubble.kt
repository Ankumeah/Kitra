package com.ankumeah.github.kitra.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ankumeah.github.kitra.models.Message
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.SampleDataViewModel

@Composable
fun MessageBubble(modifier: Modifier = Modifier, message: Message, colors: ColorsViewModel, sentByUser: Boolean = false) {
  Column(
    modifier = modifier
      .clip(RoundedCornerShape(5.dp))
      .background(colors.secondary())
      .padding(5.dp),
    horizontalAlignment = if (sentByUser) Alignment.Start else Alignment.End
  ) {
    Text(text = message.text, color = colors.text())
    Text(text = message.timestamp.toString(), color = colors.primary(), fontSize = 10.sp)
  }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun MessageBubblePreview() {
  Box(Modifier.padding(10.dp)) {
    MessageBubble(message = SampleDataViewModel().messageList[0], colors = ColorsViewModel())
  }
}