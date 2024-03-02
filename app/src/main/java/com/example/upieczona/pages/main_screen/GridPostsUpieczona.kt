package com.example.upieczona.pages.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.static_object.MaterialsUtils.decodeHtml
import com.example.upieczona.ui.theme.colorPink

@Composable
fun GridPostsUpieczona(
  allPosts: State<List<PostsOfUpieczonaItemDto>>,
  scrollState: LazyGridState,
  navController: NavHostController,
  paddingValues: PaddingValues,
) {
  LazyVerticalGrid(modifier = Modifier.padding(paddingValues),
    columns = GridCells.Fixed(2),
    state = scrollState,
    content = {
      items(allPosts.value.size) { index ->
        val decodedTextPostName = allPosts.value[index].title.rendered.decodeHtml()

        val imageUrl = allPosts.value[index].yoastHeadJson.ogImage?.getOrNull(0)?.url
        val twitterImageUrl = allPosts.value[index].yoastHeadJson.twitterImage
        val selectedImageUrl = imageUrl ?: twitterImageUrl

        Box(modifier = Modifier
          .background(colorPink)
          .padding(8.dp)
          .aspectRatio(0.6f)
          .shadow(elevation = 9.dp, shape = RoundedCornerShape(15.dp))
          .clickable {
            navController.navigate("Content/${allPosts.value[index].id}")
          }
          .background(Color.White), contentAlignment = Alignment.Center) {
          Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {

            //Image section
            Box(
              modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding()
                .background(Color.White),
              contentAlignment = Alignment.Center
            ) {
              val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = selectedImageUrl).apply {
                  crossfade(true)
                  memoryCachePolicy(CachePolicy.ENABLED)
                  diskCachePolicy(CachePolicy.ENABLED)
                }.build()
              )
              Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
            }


            //Text section
            val maxTextLength = 30
            Text(
              modifier = Modifier
                .padding(start = 6.dp, end = 6.dp, top = 6.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White),
              fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
              textAlign = TextAlign.Center,
              color = Color(0xFF000000),
              text = if (decodedTextPostName.length > maxTextLength) {
                decodedTextPostName.take(maxTextLength) + "..."
              } else {
                decodedTextPostName
              },
              fontSize = 13.sp,
              maxLines = 2,
              overflow = TextOverflow.Ellipsis
            )
          }
        }
      }
    })
}

