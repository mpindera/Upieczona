package com.example.upieczona.pages.content_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.upieczona.static_object.MaterialsUtils.decodeHtml
import com.example.upieczona.viewmodel.UpieczonaAPIViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentScreenUpieczona(
  postIndex: Int?,
  navController: NavHostController,
  upieczonaAPIViewModel: UpieczonaAPIViewModel,
  paddingValues: PaddingValues
) {
  val postDetails = remember(upieczonaAPIViewModel.allPosts) {
    postIndex?.let { index ->
      upieczonaAPIViewModel.allPosts.value.find { it.id == index }
    }
  }


  val urlsListPhotosUpieczona = remember(postDetails) {
    postDetails?.content?.let { upieczonaAPIViewModel.extractPhotosUrls(it.rendered) }
  }

  val decodedTextFromTitleUpieczona = remember(postDetails) {
    postDetails?.title?.rendered?.decodeHtml()
  }


  val scaffoldState = rememberBottomSheetScaffoldState()
  var selectedTabIndex by remember {
    mutableStateOf(0)
  }
  var selectedTab by remember {
    mutableStateOf(false)
  }

  BottomSheetScaffold(
    scaffoldState = scaffoldState,
    sheetPeekHeight = BottomSheetScaffoldDefaults.SheetPeekHeight,
    sheetDragHandle = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
      ) {
        TabRow(
          selectedTabIndex = selectedTabIndex,
          modifier = Modifier
            .fillMaxWidth()
            .align(CenterVertically)
        ) {
          Tab(
            text = { Text("Składniki") },
            selected = selectedTab,
            onClick = {
              selectedTabIndex = 0
              selectedTab = true
            }
          )
          Tab(
            text = { Text("Przepis") },
            selected = selectedTab,
            onClick = {
              selectedTabIndex = 1
              selectedTab = true
            }
          )
          Tab(
            text = { Text("Info") },
            selected = selectedTab,
            onClick = {
              selectedTabIndex = 2
              selectedTab = true
            }
          )
        }
      }
    },
    sheetContent = {
      when (selectedTabIndex) {
        0 -> {Ingredients()}
        1 -> {Directions()}
        2 -> {Info()}
      }
    }) {


    Column(
      modifier = Modifier.padding(paddingValues)
    ) {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(start = 13.dp, end = 13.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
      ) {
        item {
          if (urlsListPhotosUpieczona != null) {
            ImagePager(
              urlsListPhotosUpieczona.size, urlsListPhotosUpieczona
            )
          }
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .align(Alignment.Start)
          ) {
            if (decodedTextFromTitleUpieczona != null) {
              Text(
                modifier = Modifier.padding(top = 7.dp, bottom = 5.dp),
                fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
                text = decodedTextFromTitleUpieczona,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun Ingredients() {
  LazyColumn(content = {
    items(10) {
      Text(text = "")
    }
  })
}
@Composable
fun Directions() {
  LazyColumn(content = {
    items(10) {
      Text(text = "")
    }
  })
}
@Composable
fun Info() {
  LazyColumn(content = {
    items(10) {
      Text(text = "")
    }
  })
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(imageUrlsSize: Int, imageUrls: List<String>) {
  val pageState = rememberPagerState(pageCount = { imageUrls.size })
  HorizontalPager(
    state = pageState
  ) { pageIndex ->
    val imageUrl = imageUrls[pageIndex]
    Row(
      modifier = Modifier
        .height(400.dp)
        .width(400.dp)
        .background(Color.White),
      horizontalArrangement = Arrangement.Center
    ) {
      AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.fillMaxSize()
      )
    }
  }
  Row(
    modifier = Modifier
      .height(20.dp)
      .fillMaxSize(),
    verticalAlignment = Alignment.Bottom,
    horizontalArrangement = Arrangement.Center
  ) {
    repeat(imageUrlsSize) { iteration ->
      val color = if (pageState.currentPage == iteration) Color.DarkGray else Color.LightGray
      Box(
        modifier = Modifier
          .padding(2.dp)
          .clip(CircleShape)
          .background(color = color)
          .size(7.dp)
      )
    }
  }
}