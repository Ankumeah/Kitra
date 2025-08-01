package com.ankumeah.github.kitra.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ankumeah.github.kitra.composables.ContactList
import com.ankumeah.github.kitra.composables.TitleBar
import com.ankumeah.github.kitra.ui.theme.KitraTheme
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.SampleDataViewModel

@Composable
fun MainScreen(
  navController: NavController,
  modifier: Modifier = Modifier
) {
  val sampleData =  remember { SampleDataViewModel() } //TODO add an actual local db
  val colors = remember { ColorsViewModel() }

  Column(modifier = modifier.background(colors.secondary())) {
    TitleBar(modifier = Modifier.fillMaxWidth().weight(0.1f))
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .weight(0.9f)
        .padding(10.dp)
        .clip(RoundedCornerShape(15.dp))
        .background(colors.primary())
    ) {
      ContactList(
        contacts = sampleData.contactList, //TODO add an actual local db
        navController = navController,
        modifier = Modifier
          .fillMaxWidth()
          .weight(0.9f),
        contactModifier = Modifier
          .padding(horizontal = 5.dp, vertical = 5.dp)
          .fillMaxWidth()
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
  KitraTheme {
    MainScreen(modifier = Modifier.fillMaxSize(), navController = rememberNavController())
  }
}