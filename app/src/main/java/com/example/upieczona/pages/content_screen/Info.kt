package com.example.upieczona.pages.content_screen

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.upieczona.R
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.roomdatabase.Note
import com.example.upieczona.ui.theme.colorPink
import com.example.upieczona.viewmodel.NoteViewModel

@Composable
fun Info(
  postDetails: PostsOfUpieczonaItemDto?,
  paddingValues: PaddingValues,
  viewModel: NoteViewModel = hiltViewModel()
) {

  var columnWidthDp by remember { mutableStateOf(0.dp) }
  val density = LocalDensity.current

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(paddingValues)
  ) {
    postDetails?.id?.let { viewModel.loadNotesById(it) }
    val contents by viewModel.notes.collectAsStateWithLifecycle()

    LazyColumn {
      items(contents) { note ->
        Column(modifier = Modifier.padding(top = 20.dp)) {
          Box {

            FirstCircle(columnWidthDp = columnWidthDp, note = note, onNoteEdited = { newTitle ->
              note.postId?.let {
                viewModel.updateNote(
                  Note(id = note.id, title = newTitle, postId = note.postId), postId = it
                )
              }
            })

            SecondCircle(note)

            ThirdCircle(
              columnWidthDp = columnWidthDp,
              postDetails = postDetails,
              viewModel = viewModel,
              note = note
            )

            ElevatedCard(
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp, start = 15.dp, end = 15.dp, top = 15.dp)
                .onGloballyPositioned { coordintes ->
                  columnWidthDp = with(density) { coordintes.size.width.toDp() }
                }, elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
              ), colors = CardDefaults.cardColors(
                containerColor = colorPink
              )
            ) {
              Text(
                modifier = Modifier.padding(20.dp),
                fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
                text = note.title,
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

@Composable
private fun FirstCircle(
  columnWidthDp: Dp, note: Note, onNoteEdited: (String) -> Unit
) {
  var showDialog by remember { mutableStateOf(false) }
  val currentTitle = remember { mutableStateOf(note.title) }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .zIndex(1f), contentAlignment = TopStart
  ) {
    Box(Modifier.padding(start = columnWidthDp / 6)) {
      Canvas(modifier = Modifier
        .size(35.dp)
        .clickable {
          showDialog = true
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
        imageVector = Icons.Filled.Create,
        contentDescription = null
      )
    }
  }

  if (showDialog) {
    AlertDialog(onDismissRequest = { showDialog = false },
      title = { Text(text = stringResource(id = R.string.edit_note)) },
      text = {
        OutlinedTextField(
          value = currentTitle.value,
          onValueChange = { currentTitle.value = it },
          modifier = Modifier.fillMaxWidth()
        )
      },
      confirmButton = {
        Button(onClick = {
          onNoteEdited(currentTitle.value)
          showDialog = false
        }) {
          Text(stringResource(id = R.string.save_note))
        }
      },
      dismissButton = {
        Button(onClick = { showDialog = false }) {
          Text(stringResource(id = R.string.cancel))
        }
      })
  }
}

@Composable
private fun SecondCircle(note: Note) {
  val clipboardManager: ClipboardManager = LocalClipboardManager.current
  val context = LocalContext.current
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .zIndex(1f)
  ) {
    Canvas(modifier = Modifier
      .size(35.dp)
      .align(TopCenter)
      .clickable {
        clipboardManager.setText(AnnotatedString(note.title))
        Toast
          .makeText(context, "Copied text.", Toast.LENGTH_SHORT)
          .show()
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
      painter = painterResource(id = R.drawable.baseline_content_copy_24),
      contentDescription = null
    )
  }
}

@Composable
fun ThirdCircle(
  columnWidthDp: Dp, postDetails: PostsOfUpieczonaItemDto?, viewModel: NoteViewModel, note: Note
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .zIndex(1f), contentAlignment = TopEnd
  ) {
    Box(Modifier.padding(end = columnWidthDp / 6)) {
      Canvas(modifier = Modifier
        .size(35.dp)
        .clickable {
          postDetails?.id?.let { itPostId ->
            viewModel.deleteNote(
              note.id, postId = itPostId
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
  }
}
