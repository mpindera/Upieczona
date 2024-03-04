package com.example.upieczona.pages.content_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
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
import androidx.compose.material.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.upieczona.R
import com.example.upieczona.static_object.MaterialsUtils.decodeHtml
import com.example.upieczona.viewmodel.UpieczonaAPIViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ContentScreenUpieczona(
  postIndex: Int?, upieczonaAPIViewModel: UpieczonaAPIViewModel, paddingValues: PaddingValues
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
  val stringPostDetails = postDetails?.content?.rendered.toString()

  val ingredientTitleUpieczona = remember(postDetails) {
    upieczonaAPIViewModel.getCachedIngredients(stringPostDetails)
  }


  val scaffoldState = rememberBottomSheetScaffoldState()
  var selectedTabIndex by remember {
    mutableStateOf(0)
  }
  var selectedTab by remember {
    mutableStateOf(false)
  }

  var isExpanded by remember { mutableStateOf(false) }

  LaunchedEffect(scaffoldState.bottomSheetState.currentValue) {
    isExpanded = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded
  }

  val tabTitles = listOf(
    stringResource(id = R.string.ingredients),
    stringResource(id = R.string.recipe),
    stringResource(id = R.string.info)
  )

  BottomSheetScaffold(scaffoldState = scaffoldState,
    sheetTonalElevation = 250.dp,
    sheetPeekHeight = 89.dp,
    sheetDragHandle = {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(top = 10.dp)
      ) {

        AnimatedVisibility(
          visible = !isExpanded, enter = expandVertically(
            expandFrom = Alignment.Top, clip = true
          )
        ) {

          Icon(
            imageVector = if (isExpanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
            contentDescription = if (isExpanded) "down_Arrow" else "upp_Arrow",
            modifier = Modifier
              .fillMaxWidth()
              .align(CenterHorizontally)
          )
        }

        if (decodedTextFromTitleUpieczona != null) {
          Text(
            modifier = Modifier
              .align(CenterHorizontally)
              .padding(bottom = 10.dp),
            fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
            text = decodedTextFromTitleUpieczona,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
          )
        }


        TabRow(
          selectedTabIndex = selectedTabIndex, modifier = Modifier.fillMaxWidth()
        ) {
          tabTitles.forEachIndexed { index, title ->
            Tab(text = { Text(title) }, selected = selectedTabIndex == index, onClick = {
              selectedTabIndex = index
              selectedTab = true
            })
          }
        }

        Crossfade(
          targetState = selectedTabIndex,
          animationSpec = tween(300, easing = LinearOutSlowInEasing),
          label = ""
        ) { tabIndex ->
          when (tabIndex) {
            0 -> {
              LazyColumn(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(start = 13.dp, end = 13.dp),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Top
              ) {
                item {
                  Ingredients(upieczonaAPIViewModel, stringPostDetails, ingredientTitleUpieczona)
                }
              }
            }

            1 -> {
              LazyColumn(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(start = 13.dp, end = 13.dp),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Top
              ) {
                item {
                  Recipe(upieczonaAPIViewModel, stringPostDetails)
                }
              }
            }

            2 -> {
              LazyColumn(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(start = 13.dp, end = 13.dp),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Top
              ) {
                item {
                  Info()
                }
              }
            }
          }
        }
      }
    },
    sheetContent = {}) {
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
        }
      }
    }
  }
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