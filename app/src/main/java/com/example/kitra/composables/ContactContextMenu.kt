package com.example.kitra.composables

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
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
        ConfirmContactDeleteAlert(isDisplayed = showDeleteAlert, contactName = contactName, parentIsDisplayed = expanded)
    }
}