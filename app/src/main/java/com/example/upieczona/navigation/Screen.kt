package com.example.upieczona.navigation

sealed class Screen(val route: String) {
  object SplashScreenOfUpieczona : Screen("splash_screen")
  object MainScreenOfUpieczona : Screen("main_screen")

  object ContentScreenOfUpieczona : Screen("Content/{postIndex}")
}
