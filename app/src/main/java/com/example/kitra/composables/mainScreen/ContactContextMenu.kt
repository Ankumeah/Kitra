package com.example.kitra.composables.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kitra.ui.theme.kitraColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactContextMenu(expanded: MutableState<Boolean>, modifier: Modifier = Modifier, contactName: String, offset: DpOffset = DpOffset(x = 0.dp, y = 0.dp)) {
    val showDeleteAlert = remember{ mutableStateOf(false) }

    DropdownMenu(
        expanded = expanded.value,
        containerColor = kitraColors().primary,
        onDismissRequest = { expanded.value = false },
        modifier = modifier,
        offset = offset
    ) {
        DropdownMenuItem(
            text = { Text(text = "Delete", color = kitraColors().text) },
            onClick = { showDeleteAlert.value = true }
        )
    }

    if (showDeleteAlert.value) {
        ConfirmContactDeleteAlert(
            isDisplayed = showDeleteAlert,
            contactName = contactName,
            parentIsDisplayed = expanded
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmContactDeleteAlert(modifier: Modifier = Modifier, isDisplayed: MutableState<Boolean>, contactName: String, parentIsDisplayed: MutableState<Boolean> = mutableStateOf(true)) {
    BasicAlertDialog(
        onDismissRequest = { isDisplayed.value = false },
        modifier = modifier.clip(RoundedCornerShape(10.dp))
    ) {
        Column(modifier = Modifier.background(kitraColors().primary).padding(16.dp)) {
            Text(
                text = "Confirm?",
                color = kitraColors().text,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { isDisplayed.value = false; parentIsDisplayed.value = false }) {
                    Text("Cancel", color = kitraColors().text)
                }

                TextButton(
                    onClick = {
                        println("Contact Deleted: $contactName")
                        isDisplayed.value = false
                        parentIsDisplayed.value = false
                    }
                ) {
                    Text("Confirm", color = kitraColors().text)
                }
            }
        }
    }
}