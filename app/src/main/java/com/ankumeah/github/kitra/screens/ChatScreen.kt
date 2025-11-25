package com.ankumeah.github.kitra.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ankumeah.github.kitra.models.Contact
import com.ankumeah.github.kitra.ui.theme.KitraTheme
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.SampleDataViewModel

@Composable
fun ChatScreen(modifier: Modifier = Modifier, navController: NavController, contact: Contact, colors: ColorsViewModel) {
  Column(modifier = modifier.background(colors.primary())) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .weight(0.1f)
        .background(colors.secondary())
        .padding(10.dp)
        .clip(RoundedCornerShape(15.dp))
        .background(colors.primary())
        .padding(5.dp)
    ) {
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .weight(0.2f)
          .fillMaxHeight()
          .clip(RoundedCornerShape(15.dp))
          .background(colors.secondary())
          .clickable {
            navController.navigate("MainScreen")
          }
      ) {
        Text(text = " < ", color = colors.text(), fontSize = 24.sp)
      }
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .weight(0.8f)
          .fillMaxHeight()
          .padding(start = 5.dp)
          .clip(RoundedCornerShape(15.dp))
          .background(colors.secondary())
      ) {
        Text(text = contact.contactName, color = colors.text(), fontSize = 24.sp)
      }
    }
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .weight(0.9f)
        .background(colors.secondary())
        .padding(10.dp)
        .clip(RoundedCornerShape(15.dp))
        .background(colors.primary())
    ) {

    }
  }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
  KitraTheme {
    ChatScreen(modifier = Modifier.fillMaxSize(), navController = rememberNavController(), contact = SampleDataViewModel().contactList[1], colors = ColorsViewModel())
  }
}