package com.ankumeah.github.kitra.composables

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ankumeah.github.kitra.models.Contact
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.DataBaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AddNewContactPopUp(modifier: Modifier = Modifier, dataBaseViewModel: DataBaseViewModel, colors: ColorsViewModel, onDismissRequest: () -> Unit) {
  val context = LocalContext.current
  val newContactName = remember { mutableStateOf("") }
  val newContactEmail = remember { mutableStateOf("") }
  val coroutineScope = rememberCoroutineScope()

  val outlinedTextFieldColors = TextFieldDefaults.colors(
      focusedTextColor = colors.text(),
      unfocusedTextColor = colors.text(),
      disabledTextColor = colors.text().copy(alpha = 0.5f),
      errorTextColor = colors.text(),

      cursorColor = colors.text(),
      errorCursorColor = colors.text(),

      focusedIndicatorColor = colors.text(),
      unfocusedIndicatorColor = colors.text(),
      disabledIndicatorColor = colors.text().copy(alpha = 0.5f),
      errorIndicatorColor = colors.text(),

      focusedLabelColor = colors.text(),
      unfocusedLabelColor = colors.text(),
      disabledLabelColor = colors.text().copy(alpha = 0.5f),
      errorLabelColor = colors.text(),

      focusedContainerColor = Color.Transparent,
      unfocusedContainerColor = Color.Transparent,
      disabledContainerColor = Color.Transparent,
      errorContainerColor = Color.Transparent,
    )

  AlertDialog(modifier = modifier,
    title = {
      Text(
        text = "Add Contact",
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
          colors = outlinedTextFieldColors
        )
        OutlinedTextField(
          value = newContactEmail.value,
          onValueChange = { newContactEmail.value = it },
          label = { Text(text = "Email") },
          colors = outlinedTextFieldColors
        )
      }
    },
    confirmButton = {
      TextButton( onClick = {
        coroutineScope.launch(Dispatchers.IO) {
          val userExist = dataBaseViewModel.isValidUser(newContactEmail.value)

          withContext(Dispatchers.Main) {
            when (userExist) {
              0 -> {
                try {
                  dataBaseViewModel.addContact(
                    Contact().apply {
                      contactName = newContactName.value
                      contactAddress = newContactEmail.value
                    }
                  )
                  Toast.makeText(context, "Added contact: ${newContactName.value}", Toast.LENGTH_SHORT).show()
                }
                catch (e: Exception) {
                  Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                  Log.e("AddNewContactPopUp", "Error: $e")
                }
              }

              1 -> {
                Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show()
              }

              else -> {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
              }
            }
          }

          onDismissRequest()
        }
      } ) {
        Text(text = "Add", color = colors.text())
      }
    },
    dismissButton = {
      TextButton(onClick = { onDismissRequest() }) {
        Text(text = "Cancel", color = colors.text())
      }
    },
    containerColor = colors.secondary(),
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