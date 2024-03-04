package com.example.upieczona.pages.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavHostController
import com.example.upieczona.R
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.navigation.NavigationScreen
import com.example.upieczona.viewmodel.MainViewModel
import com.example.upieczona.viewmodel.UpieczonaAPIViewModel
import kotlinx.coroutines.delay

@Composable
fun MainScreenUpieczona(
  mainViewModel: MainViewModel,
  upieczonaAPIViewModel: UpieczonaAPIViewModel,
  navController: NavHostController,
  paddingValues: PaddingValues
) {
  mainViewModel.onNavigationScreenChanged(NavigationScreen.MAIN_SCREEN)

  val categoriesState by upieczonaAPIViewModel.categoriesState.collectAsState()
  val isLoading by upieczonaAPIViewModel.isLoading.collectAsState()
  val allPostsFromCategories = upieczonaAPIViewModel.allCategoryPosts
  val post: State<List<PostsOfUpieczonaItemDto>>

  var selectedIndex by remember { mutableStateOf(0) }
  var chosenCategory by remember { mutableStateOf("") }
  var showIndicator by remember { mutableStateOf(true) }
  val scrollState by remember { mutableStateOf(LazyGridState(0)) }
  var selectValue by remember { mutableStateOf(0) }

  fun onTabClick(index: Int) {
    selectedIndex = index
  }

  LaunchedEffect(upieczonaAPIViewModel.select) {
    upieczonaAPIViewModel.select.collect { value ->
      selectValue = value
    }
  }

  when (selectValue) {
    0 -> {
      post = upieczonaAPIViewModel.allPosts
      FetchAllPosts(upieczonaAPIViewModel = upieczonaAPIViewModel)
    }

    else -> {
      post = allPostsFromCategories
      FetchAllPostsByCategories(upieczonaAPIViewModel = upieczonaAPIViewModel, select = selectValue)
    }
  }

  if (isLoading) {
    RotatingImage()
  } else {
    if (categoriesState.isEmpty()) {
      Text(text = stringResource(id = R.string.no_data))
    } else {
      ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
          when (selectValue) {
            0 -> null
            else -> Indicator(
              tabPositions,
              selectedIndex
            )
          }
        }
      ) {
        categoriesState.forEachIndexed { index, category ->
          Tab(modifier = Modifier.padding(
            start = 5.dp, top = 10.dp, end = 5.dp, bottom = 5.dp
          ), selected = (selectedIndex == index), onClick = {
            onTabClick(index)
            showIndicator = true
            selectValue = category.id
            chosenCategory = category.name
          }, text = {
            Text(
              fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
              color = Color(0xFF001018),
              text = category.name.uppercase()
            )
          })
        }
      }
      GridPostsUpieczona(
        allPosts = post,
        scrollState = scrollState,
        navController = navController,
        paddingValues = paddingValues
      )
    }
  }

}


@Composable
private fun FetchAllPosts(upieczonaAPIViewModel: UpieczonaAPIViewModel) {
  LaunchedEffect(key1 = 1) {
    upieczonaAPIViewModel.fetchAllPosts()
  }
}

@Composable
private fun FetchAllPostsByCategories(upieczonaAPIViewModel: UpieczonaAPIViewModel, select: Int) {
  LaunchedEffect(key1 = select) {
    upieczonaAPIViewModel.fetchAllPostsByCategory(select)
  }
}

@Composable
fun RotatingImage() {
  var rotationState by remember { mutableStateOf(0f) }

  LaunchedEffect(Unit) {
    while (true) {
      delay(16)
      rotationState += 2f
    }
  }

  Box(
    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
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
}

@Composable
fun Indicator(tabPositions: List<TabPosition>, selectedIndex: Int) {
  TabRowDefaults.Indicator(
    Modifier
      .tabIndicatorOffset(tabPositions[selectedIndex])
      .height(3.dp),
    color = MaterialTheme.colorScheme.primary
  )
}