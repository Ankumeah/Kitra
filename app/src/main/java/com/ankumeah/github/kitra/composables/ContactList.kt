package com.ankumeah.github.kitra.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ankumeah.github.kitra.models.Contact
import com.ankumeah.github.kitra.ui.theme.KitraTheme
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.DataBaseViewModel
import com.ankumeah.github.kitra.viewModels.SampleDataViewModel

@Composable
fun ContactBlock(modifier: Modifier = Modifier, contact: Contact, navController: NavController, colors: ColorsViewModel, dataBaseViewModel: DataBaseViewModel) {
  val showContextMenu = remember { mutableStateOf(false) }
  val showDeleteContactPopUpMenu = remember { mutableStateOf(false) }

  val contextMenuOffset = remember { mutableStateOf(DpOffset.Zero) }
  val itemHeight = remember { mutableStateOf(0.dp) }
  val density = LocalDensity.current

  Box(
    modifier = modifier
      .clip(shape = RoundedCornerShape(15.dp))
      .background(color = colors.secondary())
      .pointerInput(true) {
        detectTapGestures(
          onTap = { navController.navigate("chats/${contact.contactName}") },
          onLongPress = {
            showContextMenu.value = true
            contextMenuOffset.value = DpOffset(it.x.toDp(), it.y.toDp())
          }
        )
      }
      .onSizeChanged { itemHeight.value = with(density) { it.height.toDp() } }

      .padding(horizontal = 20.dp, vertical = 10.dp),
    contentAlignment = Alignment.CenterStart
  ) {
    Text(
      text = contact.contactName,
      color = colors.text(),
      fontSize = 24.sp
    )
  }

 ContactBlockContextMenu(
   expanded = showContextMenu.value,
   onDismissRequest = { showContextMenu.value = false },
   onClick = { showDeleteContactPopUpMenu.value = true },
   colors = colors,
   offset = contextMenuOffset.value.copy(y = contextMenuOffset.value.y - itemHeight.value)
 )

  if (showDeleteContactPopUpMenu.value) {
    DeleteContactPopUpMenu(
      onDismissRequest = { showDeleteContactPopUpMenu.value = false },
      contact = contact,
      dataBaseViewModel = dataBaseViewModel,
      colors = colors
    )
  }
}

@Composable
fun ContactList(
  contacts: List<Contact>,
  modifier: Modifier = Modifier,
  contactModifier: Modifier = Modifier,
  navController: NavController,
  colors: ColorsViewModel, dataBaseViewModel:
  DataBaseViewModel
) {
  LazyColumn(modifier = modifier) {
    items(contacts) { contact ->
      ContactBlock(
        contact = contact,
        modifier = contactModifier,
        navController = navController,
        colors = colors,
        dataBaseViewModel = dataBaseViewModel
      )
    }
  }
}

@Composable
fun ContactBlockContextMenu(
  modifier: Modifier = Modifier,
  expanded: Boolean,
  onDismissRequest: () -> Unit,
  onClick: () -> Unit,
  colors: ColorsViewModel,
  offset: DpOffset
) {
  DropdownMenu(
    modifier = modifier,
    expanded = expanded,
    onDismissRequest = onDismissRequest,
    containerColor = colors.secondary(),
    offset =  offset
  ) {
    DropdownMenuItem(
      text = { Text(text = "Delete Contact", color = Color.Red) },
      onClick = { onClick(); onDismissRequest() }
    )
  }
}

@Composable
fun DeleteContactPopUpMenu(
  modifier: Modifier = Modifier,
  onDismissRequest: () -> Unit,
  contact: Contact,
  dataBaseViewModel: DataBaseViewModel,
  colors: ColorsViewModel
) {
  AlertDialog(
    modifier = modifier,
    title = { Text(text = "Are you sure you want to delete: ${contact.contactName}", color = colors.text()) },
    confirmButton = {
      TextButton(
        onClick = { onDismissRequest(); dataBaseViewModel.deleteContact(contact = contact) }
      )
      { Text(text = "Yes", color = Color.Red) } },
    dismissButton = {
      TextButton(onClick = { onDismissRequest() }) { Text(text = "No", color = colors.text()) }
    },
    onDismissRequest = onDismissRequest,
    containerColor = colors.secondary()
  )
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun ContactBlockPreview() {
  KitraTheme {
    ContactList(
      contacts = SampleDataViewModel().contactList,
      navController = rememberNavController(),
      modifier = Modifier.fillMaxSize(),
      contactModifier = Modifier.fillMaxWidth().padding(2.dp),
      colors  = ColorsViewModel(),
      dataBaseViewModel = DataBaseViewModel()
    )
  }
}
