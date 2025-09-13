package com.ankumeah.github.kitra.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ankumeah.github.kitra.composables.ContactList
import com.ankumeah.github.kitra.composables.MainScreenTitleBar
import com.ankumeah.github.kitra.models.Contact
import com.ankumeah.github.kitra.ui.theme.KitraTheme
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.DataBaseViewModel
import com.ankumeah.github.kitra.viewModels.SampleDataViewModel

@Composable
fun MainScreen(
  navController: NavController,
  modifier: Modifier = Modifier,
  contacts: List<Contact>,
  colors: ColorsViewModel,
  dataBaseViewModel: DataBaseViewModel
) {
  Column(modifier = modifier.background(colors.secondary())) {
    MainScreenTitleBar(modifier = Modifier.fillMaxWidth().weight(0.1f), navController = navController, colors = colors, dataBaseViewModel = dataBaseViewModel)

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .weight(0.9f)
        .padding(10.dp)
        .clip(RoundedCornerShape(15.dp))
        .background(colors.primary())
    ) {
      if (contacts.isEmpty()) {
        Box(modifier = Modifier.fillMaxWidth().weight(0.9f), contentAlignment = Alignment.Center) {
          Text(
            text = "You have no contacts\nClick on the '+' above\nto add some",
            color = colors.text(),
            modifier = Modifier
              .clip(RoundedCornerShape(15.dp))
              .background(colors.secondary())
              .padding(10.dp))
        }
      }
      else {
        ContactList(
          contacts = contacts,
          navController = navController,
          colors = colors,
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
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
  KitraTheme {
    MainScreen(modifier = Modifier.fillMaxSize(), navController = rememberNavController(), contacts = SampleDataViewModel().contactList, colors = ColorsViewModel(), dataBaseViewModel = DataBaseViewModel())
  }
}