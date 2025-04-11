package com.example.kitra.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kitra.ui.theme.kitraColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactAlert(modifier: Modifier = Modifier, isDisplayed: MutableState<Boolean>) {
    val contactName = remember { mutableStateOf("") }
    val tempName = remember { mutableStateOf("") }

    BasicAlertDialog(
        modifier = modifier.clip(RoundedCornerShape(10.dp)),
        onDismissRequest = { isDisplayed.value = false }
    ) {
        Column(
            modifier = Modifier
                .background(kitraColors().primary)
                .padding(16.dp)
        ) {
            Text(
                text = "Add Contact",
                color = kitraColors().text,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(8.dp))

            TextField(
                value = tempName.value,
                onValueChange = { tempName.value = it },
                label = { Text("Contact Name") },
                singleLine = true
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { isDisplayed.value = false }) {
                    Text("Cancel", color = kitraColors().text)
                }

                TextButton(
                    onClick = {
                        contactName.value = tempName.value
                        println("New contact: ${contactName.value}")
                        isDisplayed.value = false
                    }
                ) {
                    Text("Add", color = kitraColors().text)
                }
            }
        }
    }
}
