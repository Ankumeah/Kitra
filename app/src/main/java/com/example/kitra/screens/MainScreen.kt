package com.example.kitra.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kitra.composables.ContactList
import com.example.kitra.composables.TitleBar
import com.example.kitra.ui.theme.kitraColors

@Composable
fun MainScreen(modifier: Modifier = Modifier, contacts: List<String>) {
    Column(modifier = modifier.fillMaxSize().background(kitraColors().background)) {
        TitleBar(modifier = Modifier.weight(0.1f).fillMaxSize())
        Spacer(modifier = Modifier.weight(0.025f).fillMaxSize())
        ContactList(contacts = contacts, modifier = Modifier.weight(0.875f).fillMaxSize().padding(10.dp))
    }
}