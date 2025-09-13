package com.ankumeah.github.kitra.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun ContactBlock(modifier: Modifier = Modifier, contact: Contact, navController: NavController, colors: ColorsViewModel) {
  Box(
    modifier = modifier
      .clip(shape = RoundedCornerShape(15.dp))
      .background(color = colors.secondary())
      .clickable {
        navController.navigate("chats/${contact.contactName}")
      }
      .padding(horizontal = 20.dp, vertical = 10.dp),
    contentAlignment = Alignment.CenterStart
  ) {
    Text(
      text = contact.contactName,
      color = colors.text(),
      fontSize = 24.sp
    )
  }
}

@Composable
fun ContactList(contacts: List<Contact>, modifier: Modifier = Modifier, contactModifier: Modifier = Modifier, navController: NavController, colors: ColorsViewModel) {
  LazyColumn(modifier = modifier) {
    items(contacts) { contact ->
      ContactBlock(contact = contact, modifier = contactModifier, navController = navController, colors = colors)
    }
  }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun ContactBlockPreview() {
  KitraTheme {
    ContactList(
      contacts = SampleDataViewModel().contactList,
      navController = rememberNavController(),
      modifier = Modifier.fillMaxSize(),
      contactModifier = Modifier.fillMaxWidth().padding(2.dp),
      colors  = ColorsViewModel())
  }
}