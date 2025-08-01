package com.ankumeah.github.kitra.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ankumeah.github.kitra.classes.Contact
import com.ankumeah.github.kitra.ui.theme.KitraTheme
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.SampleDataViewModel

@Composable
fun ContactBlock(modifier: Modifier = Modifier, contact: Contact, navController: NavController) {
    val colors = remember { ColorsViewModel() }
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(15.dp))
            .background(color = colors.secondary())
            .clickable {
                navController.navigate(contact.contactName)
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
fun ContactList(contacts: List<Contact>, modifier: Modifier = Modifier, contactModifier: Modifier = Modifier, navController: NavController) {
    LazyColumn(modifier = modifier) {
        items(contacts) { contact ->
            ContactBlock(contact = contact, modifier = contactModifier, navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactBlockPreview() {
    KitraTheme {
        ContactList(contacts = SampleDataViewModel().contactList, navController = rememberNavController())
    }
}