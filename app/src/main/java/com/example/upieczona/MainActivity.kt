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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.upieczona.navigation.Screen
import com.example.upieczona.splash_screen.SplashScreen
import com.example.upieczona.ui.theme.UpieczonaTheme
import com.example.upieczona.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
  private val mainViewModel by viewModels<MainViewModel>()

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()
      val context = LocalContext.current

      UpieczonaTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          Scaffold(
            topBar = { }, modifier = Modifier.fillMaxSize()
          ) { paddingValues ->
            NavHost(
              navController = navController,
              startDestination = Screen.Splash.route,
              modifier = Modifier.padding(paddingValues = paddingValues)
            ) {
              composable(Screen.Splash.route) {
                SplashScreen(mainViewModel)
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