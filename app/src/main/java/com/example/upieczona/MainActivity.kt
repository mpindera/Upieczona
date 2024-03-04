package com.example.upieczona

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.upieczona.bar.top.TopAppBarUpieczona
import com.example.upieczona.navigation.Screen
import com.example.upieczona.pages.content_screen.ContentScreenUpieczona
import com.example.upieczona.pages.main_screen.MainScreenUpieczona
import com.example.upieczona.pages.splash_screen.SplashScreen
import com.example.upieczona.ui.theme.UpieczonaTheme
import com.example.upieczona.viewmodel.MainViewModel
import com.example.upieczona.viewmodel.UpieczonaAPIViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val mainViewModel by viewModels<MainViewModel>()

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()
      val context = LocalContext.current

      UpieczonaTheme {
        val upieczonaAPIViewModel = hiltViewModel<UpieczonaAPIViewModel>()

        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          Scaffold(
            topBar = {
              TopAppBarUpieczona(
                mainViewModel = mainViewModel, navController = navController
              )
            }, modifier = Modifier.fillMaxSize()
          ) { paddingValues ->
            NavHost(
              navController = navController,
              startDestination = Screen.SplashScreenOfUpieczona.route,
              modifier = Modifier.padding(paddingValues = paddingValues)
            ) {
              composable(Screen.SplashScreenOfUpieczona.route) {
                SplashScreen(mainViewModel = mainViewModel, navController = navController)
              }

              composable(Screen.MainScreenOfUpieczona.route) {
                MainScreenUpieczona(
                  mainViewModel = mainViewModel,
                  upieczonaAPIViewModel = upieczonaAPIViewModel,
                  navController = navController,
                  paddingValues = paddingValues
                )
              }
              composable(
                Screen.ContentScreenOfUpieczona.route, arguments = listOf(navArgument("postIndex") {
                  type = NavType.IntType
                })
              ) { backStackEntry ->
                val postIndex = backStackEntry.arguments?.getInt("postIndex")
                ContentScreenUpieczona(
                  postIndex = postIndex,
                  upieczonaAPIViewModel = upieczonaAPIViewModel,
                  paddingValues = paddingValues
                )
              }
            }
          }
        }
      }
    }
  }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  UpieczonaTheme {

  }
}