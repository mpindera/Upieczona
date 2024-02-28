package com.example.upieczona.viewmodel

import androidx.lifecycle.ViewModel
import com.example.upieczona.navigation.NavigationScreen
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel(){
  private var _navigationScreen = MutableStateFlow(NavigationScreen.SPLASH_SCREEN)
  val navigationScreen: MutableStateFlow<NavigationScreen> = _navigationScreen

  fun onNavigationScreenChanged(navigationScreen: NavigationScreen) {
    _navigationScreen.value = navigationScreen
  }


}