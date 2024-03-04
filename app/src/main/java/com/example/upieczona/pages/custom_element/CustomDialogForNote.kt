package com.example.upieczona.pages.custom_element

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.upieczona.roomdatabase.NoteDao

@Composable
fun CustomDialogForNote(
  postId: Int,
  showDialog: Boolean,
  onDismiss: () -> Unit,
  onSubmit: (String) -> Unit,
  noteText: String,
  setNoteText: (String) -> Unit,
) {
  if (showDialog) {
    AlertDialog(
      onDismissRequest = onDismiss,
      title = { Text("Dodaj nową notatkę", fontSize = 15.sp) },
      text = {
        OutlinedTextField(
          value = noteText,
          onValueChange = setNoteText,
          placeholder = { Text("Wprowadź swoją notatkę") }
        )
      },
      confirmButton = {
        Button(
          onClick = {
            onSubmit(noteText)

            onDismiss()
          }
        ) {
          Text("Dodaj")
        }
      },
      dismissButton = {
        Button(
          onClick = onDismiss
        ) {
          Text("Anuluj")
        }
      }
    )
  }
}
