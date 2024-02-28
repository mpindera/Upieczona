package com.example.upieczona.navigation

sealed class Screen(val route: String) {
  object Splash : Screen("splash_screen")
  object MainScreen : Screen("main_screen")
}
