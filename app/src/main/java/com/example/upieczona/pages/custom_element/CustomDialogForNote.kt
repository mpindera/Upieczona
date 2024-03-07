package com.example.upieczona.pages.custom_element

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.upieczona.R
import com.example.upieczona.roomdatabase.Note
import com.example.upieczona.roomdatabase.NoteDao
import com.example.upieczona.viewmodel.NoteViewModel

@Composable
fun CustomDialogForNote(
  postId: Int,
  showDialog: Boolean,
  onDismiss: () -> Unit,
  noteText: String,
  setNoteText: (String) -> Unit,
  resetNoteText: () -> Unit,
  viewModel: NoteViewModel = hiltViewModel()
) {

  val onSubmit: (value: Note) -> Unit = remember {
    return@remember viewModel::insertNoteToDatabase
  }

  if (showDialog) {
    AlertDialog(onDismissRequest = {
      resetNoteText()
      onDismiss()
    }, title = { Text(stringResource(id = R.string.add_note), fontSize = 15.sp) }, text = {
      OutlinedTextField(value = noteText,
        onValueChange = setNoteText,
        placeholder = { Text(stringResource(id = R.string.enter_note)) })
    }, confirmButton = {
      Button(onClick = {
        onSubmit(
          Note(title = noteText, postId = postId)
        )
        resetNoteText()
        onDismiss()
      }) {
        Text(stringResource(id = R.string.add))
      }
    }, dismissButton = {
      Button(onClick = {
        resetNoteText()
        onDismiss()
      }) {
        Text(stringResource(id = R.string.cancel))
      }
    })
  }
}
