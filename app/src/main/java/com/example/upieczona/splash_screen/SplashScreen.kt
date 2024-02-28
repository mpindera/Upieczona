package com.example.upieczona.splash_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.upieczona.R
import com.example.upieczona.navigation.NavigationScreen
import com.example.upieczona.ui.theme.colorPinkMain
import com.example.upieczona.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(mainViewModel: MainViewModel) {

  mainViewModel.onNavigationScreenChanged(NavigationScreen.SPLASH_SCREEN)

  Box(
    contentAlignment = Alignment.Center, modifier = Modifier
      .fillMaxSize()
  ) {
    var visible by remember {
      mutableStateOf(false)
    }
    AnimatedVisibility(
      visible = visible,
      enter = expandVertically(animationSpec = tween(durationMillis = 2000)),
      exit = fadeOut(animationSpec = tween(durationMillis = 2000))
    ) {
      Text(
        text = stringResource(id = R.string.app_name),
        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
        modifier = Modifier
          .fillMaxSize()
          .wrapContentSize(Alignment.Center),
        color = colorPinkMain
      )
    }
    LaunchedEffect(Unit) {
      delay(500)
      visible = true
      delay(3000)
      visible = false
      delay(1500)
    }
  }
}