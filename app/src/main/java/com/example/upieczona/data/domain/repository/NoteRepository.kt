package com.example.upieczona.data.domain.repository

import androidx.room.Update
import com.example.upieczona.roomdatabase.Note

interface NoteRepository {

  suspend fun insertNote(note: Note)
  suspend fun getNotesByPostId(postId: Int): List<Note>

  suspend fun deleteNoteFromDatabase(id: Int)

  suspend fun updateNoteFromDatabase(note: Note)

}