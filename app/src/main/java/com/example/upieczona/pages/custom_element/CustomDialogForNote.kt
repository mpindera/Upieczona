package com.example.upieczona.pages.custom_element

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
  viewModel: NoteViewModel = hiltViewModel()
) {
  val onSubmit: (value: Note) -> Unit = remember {
    return@remember viewModel::insertNote
  }
  if (showDialog) {
    AlertDialog(onDismissRequest = onDismiss,
      title = { Text("Dodaj nową notatkę", fontSize = 15.sp) },
      text = {
        OutlinedTextField(value = noteText,
          onValueChange = setNoteText,
          placeholder = { Text("Wprowadź swoją notatkę") })
      },
      confirmButton = {
        Button(onClick = {
          onSubmit(
            Note(title = noteText, postId = postId)
          )
          Log.d("test","dodane")
          onDismiss()
        }) {
          Text("Dodaj")
        }
      },
      dismissButton = {
        Button(
          onClick = onDismiss
        ) {
          Text("Anuluj")
        }
      })
  }
}
