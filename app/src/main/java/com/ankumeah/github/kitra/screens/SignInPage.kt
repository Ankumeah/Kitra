package com.ankumeah.github.kitra.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ankumeah.github.kitra.composables.TitleBar
import com.ankumeah.github.kitra.ui.theme.KitraTheme
import com.ankumeah.github.kitra.viewModels.ColorsViewModel

@Composable
fun SignInPage(modifier: Modifier = Modifier, navController: NavController, colors: ColorsViewModel) {

  Column(modifier = modifier.background(colors.primary())) {
    TitleBar(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f), colors = colors)
    Box(
      modifier = Modifier
        .fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "Sign In/Up",
        color = colors.text(),
        fontSize = 42.sp,
        modifier = Modifier
          .clip(shape = RoundedCornerShape(15.dp))
          .background(color = colors.secondary())
          .clickable {
            navController.navigate("auth/GoogleSignIn")
          }
          .padding(10.dp)
      )
    }
  }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun SignInPagePreview() {
  KitraTheme {
    SignInPage(modifier = Modifier.fillMaxSize(), navController = rememberNavController(), colors = ColorsViewModel())
  }
}
