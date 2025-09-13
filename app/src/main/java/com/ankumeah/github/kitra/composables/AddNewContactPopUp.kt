package com.ankumeah.github.kitra.composables

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ankumeah.github.kitra.models.Contact
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.DataBaseViewModel

@Composable
fun AddNewContactPopUp(modifier: Modifier = Modifier, dataBaseViewModel: DataBaseViewModel, colors: ColorsViewModel, onDismissRequest: () -> Unit) {
  val context = LocalContext.current
  val newContactName = remember { mutableStateOf("") }
  val newContactEmail = remember { mutableStateOf("") }

  AlertDialog(modifier = modifier,
    title = {
      Text(
        text = "Add / Update Contact",
        color = colors.text(),
        fontSize = 24.sp
      )
    },
    text = {
      Column() {
        OutlinedTextField(
          value = newContactName.value,
          onValueChange = { newContactName.value = it },
          label = { Text(text = "Name") },
        )
        OutlinedTextField(
          value = newContactEmail.value,
          onValueChange = { newContactEmail.value = it },
          label = { Text(text = "Email") },
        )
      }
    },
    confirmButton = {
      TextButton( onClick = {
        try {
          dataBaseViewModel.addContact(
            Contact().apply {
              contactName = newContactName.value
              contactAddress = newContactEmail.value
            }
          )
          Toast.makeText(context, "Added contact: $newContactName", Toast.LENGTH_SHORT).show()
        }
        catch (e: Error) {
          Toast.makeText(context, "Error, check logs for details", Toast.LENGTH_SHORT).show()
          println("Error: $e")
        }
        onDismissRequest()
      } ) {
        Text(text = "Add", color = colors.text())
      }
    },
    dismissButton = {
      TextButton(onClick = { onDismissRequest() }) {
        Text(text = "Cancel", color = colors.text())
      }
    },
    containerColor = colors.primary(),
    onDismissRequest = onDismissRequest
  )
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun AddNewContactPopUpPreview() {
  AddNewContactPopUp(
    colors = ColorsViewModel(),
    onDismissRequest = { Unit },
    dataBaseViewModel = DataBaseViewModel()
  )
}