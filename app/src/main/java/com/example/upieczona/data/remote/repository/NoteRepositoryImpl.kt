package com.example.upieczona.data.remote.repository

import androidx.lifecycle.ViewModel
import com.example.upieczona.roomdatabase.Note
import com.example.upieczona.roomdatabase.NoteDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) {
  suspend fun getAllNote(postId: Int): List<Note> {
    return noteDao.getNotesByPostId(postId = postId)
  }

  suspend fun insertTask(note: Note) {
    return noteDao.insert(note)
  }
}