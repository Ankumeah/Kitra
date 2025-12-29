package com.ankumeah.github.kitra.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ankumeah.github.kitra.models.Contact
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.DataBaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SendMessageInputBox(modifier: Modifier = Modifier, colors: ColorsViewModel, dataViewModel: DataBaseViewModel, contact: Contact) {
  val scope = rememberCoroutineScope()
  val context = LocalContext.current
  val inputValue = remember { TextFieldState() }

  Row(modifier = modifier.padding(5.dp)) {
    BasicTextField(
      modifier = Modifier
        .fillMaxHeight()
        .weight(0.85f)
        .padding(end = 5.dp)
        .clip(RoundedCornerShape(15.dp))
        .background(colors.secondary())
        .padding(start = 10.dp, top = 10.dp, bottom = 10.dp),
      state = inputValue,
      textStyle = LocalTextStyle.current.copy(color = colors.text(), fontSize = 18.sp)
    )
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(15.dp))
        .fillMaxHeight()
        .weight(0.15f)
        .background(colors.secondary())
        .clickable {
          sendMessage(
            contact = contact,
            content = inputValue.text.toString(),
            context = context,
            dataBaseViewModel = dataViewModel,
            scope = scope
          )
          inputValue.edit { delete(0, inputValue.text.length) }
        },
      contentAlignment = Alignment.Center
    ) {
      Text(
        modifier = Modifier,
        text = "=>",
        color = colors.primary(),
        fontSize = 28.sp
      )
    }
  }
}

fun sendMessage(contact: Contact, content: String, context: Context, dataBaseViewModel: DataBaseViewModel, scope: CoroutineScope) {
  scope.launch {
    val res = dataBaseViewModel.sendMessage(contact = contact, content = content)

    if (res == false) {
      withContext(Dispatchers.Main) {
        Toast.makeText(context, "", Toast.LENGTH_LONG).show()
      }
    }
  }
}