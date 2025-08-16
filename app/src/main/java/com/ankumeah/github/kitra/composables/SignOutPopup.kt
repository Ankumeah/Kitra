package com.ankumeah.github.kitra.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.ankumeah.github.kitra.viewModels.ColorsViewModel

@Composable
fun SignOutPopup(modifier: Modifier = Modifier, onDismissRequest: () -> Unit, onConform: () -> Unit, colors: ColorsViewModel) {

  AlertDialog(modifier = modifier,
    containerColor = colors.primary(),
    onDismissRequest = onDismissRequest,
    confirmButton = {
      TextButton(
        onClick = {
          onConform()
        }
      ) {
        Text(text = "Yes", color = Color.Red)
      }
    }, title = {
      Text(
        text = "Log Out?",
        color = colors.text(),
        fontSize = 24.sp
      )
    }, dismissButton = {
      TextButton(onClick = {
        onDismissRequest()
      }) { Text(text = "Cancel", color = colors.text()) }
    })
}