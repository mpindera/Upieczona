package com.example.upieczona.pages.content_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.static_object.MaterialsUtils
import com.example.upieczona.ui.theme.colorCardIngredient
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
        ElevatedCard(
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp, start = 15.dp, end = 15.dp, top = 10.dp),
          elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
          ),
          colors = CardDefaults.cardColors(
            containerColor = colorPink
          )
        ) {

          Text(
            modifier = Modifier.padding(10.dp),
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