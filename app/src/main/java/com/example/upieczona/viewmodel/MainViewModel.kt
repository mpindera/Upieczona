package com.example.upieczona.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.upieczona.navigation.NavigationScreen
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {
  private var _navigationScreen = MutableStateFlow(NavigationScreen.SPLASH_SCREEN)
  val navigationScreen: MutableStateFlow<NavigationScreen> = _navigationScreen

  fun onNavigationScreenChanged(navigationScreen: NavigationScreen) {
    _navigationScreen.value = navigationScreen

  }

  private var _visibleTitleTopBar = MutableStateFlow(false)
  val visibleTitleTopBar: MutableStateFlow<Boolean> = _visibleTitleTopBar
  fun onVisibleTitleTopAppBarChanged(visibleTitleTopBar: Boolean) {
    _visibleTitleTopBar.value = visibleTitleTopBar
  }
}