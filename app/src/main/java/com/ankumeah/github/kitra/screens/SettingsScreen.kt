package com.ankumeah.github.kitra.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ankumeah.github.kitra.auth.googleSignOut
import com.ankumeah.github.kitra.classes.Setting
import com.ankumeah.github.kitra.composables.SignOutPopup
import com.ankumeah.github.kitra.composables.TitleBar
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.DataBaseViewModel

@Composable
fun SettingsScreen(modifier: Modifier = Modifier, navController: NavController, dataBaseViewModel: DataBaseViewModel, colors: ColorsViewModel) {
  val showSignOutPopup = remember { mutableStateOf(false) }
  val context = LocalContext.current

  val user = dataBaseViewModel.user.collectAsStateWithLifecycle().value.firstOrNull()
  val misc = dataBaseViewModel.misc.collectAsStateWithLifecycle().value.firstOrNull()

  val settings = listOf(
    Setting("Username", user?.username),
    Setting("Email", user?.email),
    Setting("Dark mode", misc?.isDarkTheme)
  )

  Column(modifier = modifier.background(colors.primary())) {
    val isDark = dataBaseViewModel.misc.collectAsStateWithLifecycle().value.firstOrNull()?.isDarkTheme

    TitleBar(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f), colors = colors)
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .weight(0.925f)
        .padding(10.dp)
        .clip(RoundedCornerShape(15.dp))
        .background(colors.secondary())
        .padding(10.dp)
    ) {
      Text(text = "{", color = colors.text())

      for (setting in settings) {
        if (setting.value is String) {
          SettingItem(
            colors = colors,
            modifier = Modifier.fillMaxWidth(),
            key = setting.key,
            value = setting.value
          )
        }
        else {
          SettingItem(
            colors = colors,
            modifier = Modifier.fillMaxWidth(),
            key = setting.key,
            value = {
              Box(contentAlignment = Alignment.Center) {
                Text(
                  text = setting.value.toString(),
                  color = colors.text(),
                  modifier = Modifier
                    .clip(RoundedCornerShape(2.dp))
                    .background(colors.primary())
                    .padding(horizontal = 5.dp)
                    .clickable {
                      dataBaseViewModel.colors.swapTheme()
                      dataBaseViewModel.updateMisc()
                    }
                )
              }
            }
          )
        }
      }

      Text(text = "}", color = colors.text())
    }
    Row(modifier = Modifier.fillMaxWidth().weight(0.075f)) {
      Box(
        modifier = Modifier
          .fillMaxHeight()
          .weight(0.2f)
          .padding(10.dp)
          .clip(RoundedCornerShape(15.dp))
          .background(colors.secondary())
          .clickable {
            navController.navigate("MainScreen")
          },
        contentAlignment = Alignment.Center
      ) {
        Text(text = "<", color = colors.text(), fontSize = 20.sp)
      }
      Spacer(modifier = Modifier.weight(0.4f))
      Box(
        modifier = Modifier
          .fillMaxHeight()
          .weight(0.4f)
          .padding(10.dp)
          .clip(RoundedCornerShape(15.dp))
          .background(brush = Brush.linearGradient(listOf(Color.Red, colors.secondary(), colors.secondary())))
          .clickable {
            showSignOutPopup.value = true
          },
        contentAlignment = Alignment.Center
      ) {
        Text(text = "Sign Out", color = colors.text(), fontSize = 20.sp, fontStyle = FontStyle.Italic)
      }
    }
    if (showSignOutPopup.value) {
      SignOutPopup(
        colors = colors,
        onDismissRequest = { showSignOutPopup.value = false },
        onConform = {
          showSignOutPopup.value = false
          googleSignOut(
            context = context,
            onComplete = {
              Toast.makeText(context, "Signed Out", Toast.LENGTH_LONG).show()
              dataBaseViewModel.removeUserEmail()
            }
          )
          navController.navigate("SignInPage") {
            popUpTo("SettingsScreen") { inclusive = true }
          }
        }
      )
    }
  }
}

@Composable
fun SettingItem(modifier: Modifier = Modifier, nestingLevel: Int = 0, key: String, value: String, colors: ColorsViewModel) {
  Row(modifier = modifier.padding(start = ((nestingLevel * 20) + 10).dp)) {
    Text(text = "\"${key}\"", color = colors.text())
    Text(text = ":  ", color = colors.text())
    Text(text = "\"${value}\",", color = colors.text())
  }
}

@Composable
fun SettingItem(modifier: Modifier, nestingLevel: Int = 0, key: String, value: @Composable () -> Unit, colors: ColorsViewModel) {
  Row(modifier = modifier.padding(start = ((nestingLevel * 10) + 10).dp)) {
    Text(text = "\"${key}\"", color = colors.text())
    Text(text = ":  ", color = colors.text())
    value()
  }
}