package com.example.kitra.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kitra.composables.mainScreen.AddContactButton
import com.example.kitra.composables.mainScreen.ContactList
import com.example.kitra.ui.theme.kitraColors

@Composable
fun MainScreen(modifier: Modifier = Modifier, contacts: List<String>, navController: NavHostController) {
    Column(modifier = modifier.fillMaxSize().background(kitraColors().background).padding(bottom = 15.dp)) {
        Row(modifier = Modifier.weight(0.1f).fillMaxSize().padding(top = 10.dp)) {
            Text(text = " Kitra ", color = kitraColors().text, fontSize = 40.sp)
        }
        ContactList(contacts = contacts, modifier = Modifier.weight(0.9f).fillMaxSize().padding(10.dp), navController = navController)
        Row(modifier = Modifier.height(40.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            AddContactButton(modifier = Modifier.size(40.dp))
        }
    }
}