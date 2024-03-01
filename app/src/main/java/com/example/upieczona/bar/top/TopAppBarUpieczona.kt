package com.example.upieczona.bar.top

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.upieczona.R
import com.example.upieczona.navigation.Screen
import com.example.upieczona.ui.theme.colorPinkMain
import com.example.upieczona.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarUpieczona(mainViewModel: MainViewModel, navController: NavHostController) {
  val visibleTitleTopBar by mainViewModel.visibleTitleTopBar.collectAsState()

  TopAppBar(
    title = {
      AnimatedVisibility(
        visible = visibleTitleTopBar,
        enter = expandVertically(
          expandFrom = Alignment.Bottom,
          animationSpec = tween(durationMillis = 2000)
        )
      ) {
        Text(
          text = stringResource(id = R.string.app_name),
          fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
          modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .clickable {
              navController.navigate(Screen.Splash.route)
              mainViewModel.onVisibleTitleTopAppBarChanged(false)
            }
        )
      }
    }, colors = TopAppBarDefaults.smallTopAppBarColors(
      containerColor = colorPinkMain
    )
  )

}