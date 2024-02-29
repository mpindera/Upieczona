package com.example.upieczona.pages.splash_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.upieczona.R
import com.example.upieczona.navigation.NavigationScreen
import com.example.upieczona.navigation.Screen
import com.example.upieczona.ui.theme.colorPinkMain
import com.example.upieczona.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(mainViewModel: MainViewModel, navController: NavHostController) {

  mainViewModel.onNavigationScreenChanged(NavigationScreen.SPLASH_SCREEN)

  var rotationState by remember { mutableStateOf(0f) }

  LaunchedEffect(Unit) {
    while (true) {
      delay(16)
      rotationState += 2f
    }
  }

  Box(
    contentAlignment = Alignment.Center, modifier = Modifier
      .fillMaxSize()
  ) {

    var visible by remember {
      mutableStateOf(false)
    }

    AnimatedVisibility(
      visible = visible,
      enter = expandVertically(
        expandFrom = Alignment.Bottom,
        animationSpec = tween(durationMillis = 2000)
      ),
      exit = fadeOut(animationSpec = tween(durationMillis = 2000))
    ) {
      Text(
        text = stringResource(id = R.string.app_name),
        fontSize = 45.sp,
        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
        modifier = Modifier
          .fillMaxSize()
          .wrapContentSize(Alignment.Center)
          .padding(bottom = 95.dp),
        color = Color.Black
      )
    }


    AnimatedVisibility(
      visible = visible,
      enter = expandVertically(
        expandFrom = Alignment.Top,
        clip = true,
        animationSpec = tween(durationMillis = 2000)
      ),
      exit = fadeOut(animationSpec = tween(durationMillis = 3000))
    ) {
      Image(
        painter = painterResource(id = R.drawable.rotatingimage),
        contentDescription = null,
        modifier = Modifier
          .size(50.dp)
          .graphicsLayer(
            rotationZ = rotationState
          )
      )
    }

    LaunchedEffect(Unit) {
      delay(1000)
      visible = true
      delay(3000)
      visible = false
      delay(1500)
      mainViewModel.onVisibleTitleTopAppBarChanged(true)
      navController.navigate(Screen.MainScreen.route)
    }
  }
}