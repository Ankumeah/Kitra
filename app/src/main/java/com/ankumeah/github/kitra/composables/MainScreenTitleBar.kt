package com.ankumeah.github.kitra.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ankumeah.github.kitra.Branding
import com.ankumeah.github.kitra.viewModels.ColorsViewModel
import com.ankumeah.github.kitra.viewModels.DataBaseViewModel

@Composable
fun MainScreenTitleBar(modifier: Modifier = Modifier, navController: NavController, colors: ColorsViewModel, dataBaseViewModel: DataBaseViewModel) {
  val branding: Branding = viewModel()
  val showAddNewContactPopUp = remember { mutableStateOf(false) }

  Row(modifier = modifier.background(color = colors.secondary())) {
    Box(
      modifier = Modifier
        .fillMaxHeight()
        .weight(0.75f)
        .padding(horizontal = 10.dp),
      contentAlignment = Alignment.CenterStart
    ) {
      Text(
        text = branding.appName,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = colors.text(),
        textAlign = TextAlign.Center
      )
    }
    Box(
      modifier = Modifier
        .fillMaxHeight()
        .weight(0.25f),
      contentAlignment = Alignment.Center
    ) {
      Box(
        modifier = Modifier
          .aspectRatio(1f)
          .padding(10.dp)
          .clip(RoundedCornerShape(15.dp))
          .background(color = colors.primary())
          .clickable { showAddNewContactPopUp.value = true }
          .padding(5.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(colors.secondary()),
        contentAlignment = Alignment.Center
      ) {
        Text(text = "+", color = colors.primary(), fontSize = 30.sp)
      }
    }
    Box(
      modifier = Modifier
        .fillMaxHeight()
        .weight(0.25f),
      contentAlignment = Alignment.Center
    ) {
      Box(
        modifier = Modifier
          .aspectRatio(1f)
          .padding(10.dp)
          .clip(RoundedCornerShape(15.dp))
          .background(color = colors.primary())
          .clickable { navController.navigate("SettingsScreen") }
          .padding(5.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(colors.secondary()),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "=",
          color = colors.primary(),
          fontSize = 30.sp
        )
      }
    }
  }

  if (showAddNewContactPopUp.value) {
    AddNewContactPopUp(
      colors = colors,
      onDismissRequest = { showAddNewContactPopUp.value = false },
      dataBaseViewModel = dataBaseViewModel
    )
  }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun TitleBarPreview() {
  Row(Modifier.fillMaxSize()) {
    MainScreenTitleBar(
      modifier = Modifier.fillMaxWidth().fillMaxSize(0.1f),
      navController = rememberNavController(),
      colors = ColorsViewModel(),
      dataBaseViewModel = DataBaseViewModel()
    )
  }
}