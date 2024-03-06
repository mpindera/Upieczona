package com.example.upieczona.pages.content_screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.ui.theme.colorPink
import com.example.upieczona.viewmodel.NoteViewModel

@Composable
fun Info(
  postDetails: PostsOfUpieczonaItemDto?,
  paddingValues: PaddingValues,
  viewModel: NoteViewModel = hiltViewModel()
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(paddingValues)
  ) {
    postDetails?.id?.let { viewModel.loadNotesById(it) }
    val contents by viewModel.notes.collectAsStateWithLifecycle()

    LazyColumn {
      items(contents) {
        Column {
          Box {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
            ) {
              Canvas(modifier = Modifier
                .size(35.dp)
                .align(TopCenter)
                .clickable {
                  postDetails?.id?.let { itPostId ->
                    viewModel.deleteNote(
                      it.id, postId = itPostId
                    )
                  }
                }
                .zIndex(1f)) {
                drawCircle(
                  color = colorPink,
                  center = Offset(
                    x = size.width / 2, y = size.height / 2
                  ),
                )
              }
              Icon(
                modifier = Modifier
                  .align(Center)
                  .offset(x = 0.dp, y = 0.dp)
                  .zIndex(1f),
                imageVector = Icons.Filled.Clear,
                contentDescription = null
              )
            }
            ElevatedCard(
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp, start = 15.dp, end = 15.dp, top = 15.dp),
              elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
              ),
              colors = CardDefaults.cardColors(
                containerColor = colorPink
              )
            ) {
              Text(
                modifier = Modifier.padding(20.dp),
                fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
                text = it.title,
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
              )
            }
          }
        }
      }
    }
  }
}
