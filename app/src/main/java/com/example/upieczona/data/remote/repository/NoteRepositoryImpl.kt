package com.example.upieczona.data.remote.repository

import com.example.upieczona.roomdatabase.Note
import com.example.upieczona.roomdatabase.NoteDao
import com.example.upieczona.data.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject


class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {
  override suspend fun insertNote(note: Note) {
    withContext(IO) {
      noteDao.insertNoteToDatabase(note)
    }
  }

  override suspend fun getNotesByPostId(postId: Int): List<Note> {
    return withContext(IO) {
      noteDao.getNotesByPostIdFromDatabase(postId = postId)
    }
  }

  override suspend fun deleteNoteFromDatabase(id: Int) {
    withContext(IO) {
      noteDao.deleteNoteFromDatabase(id)
    }
  }

  override suspend fun updateNoteFromDatabase(note: Note) {
    withContext(IO) {
      noteDao.updateNoteFromDatabase(note)
    }
  }
}