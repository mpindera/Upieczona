package com.example.upieczona.data.remote.repository

import androidx.lifecycle.ViewModel
import com.example.upieczona.roomdatabase.Note
import com.example.upieczona.roomdatabase.NoteDao
import com.example.upieczona.roomdatabase.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject


class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {
  override suspend fun insert(note: Note) {
    withContext(IO) {
      noteDao.insert(note)
    }
  }

  override suspend fun getNotesByPostId(postId: Int): List<Note> {
    return withContext(IO) {
      noteDao.getNotesByPostId(postId = postId)
    }
  }
}