package com.example.kitra.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kitra.ui.theme.kitraColors

@Composable
fun ContactList(modifier: Modifier = Modifier, contacts: List<String>) {
    LazyColumn(modifier = modifier) {
        for (contactName in contacts) {
            item {
                ContactBubble(contactName = contactName, modifier = Modifier.height(100.dp).fillMaxSize().padding(vertical = 5.dp))
            }
        }
    }
}