package com.example.upieczona.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upieczona.data.remote.repository.NoteRepositoryImpl
import com.example.upieczona.roomdatabase.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
  private val noteRepository: NoteRepositoryImpl
) : ViewModel() {

  private var _onLoading by mutableStateOf(false)
  val onLoading: Boolean
    get() = _onLoading

  private val _notes = MutableStateFlow(emptyList<Note>())
  val notes = _notes.asStateFlow()

  fun loadNotesById(postId: Int) {
    viewModelScope.launch(IO) {
      _notes.value = noteRepository.getNotesByPostId(postId = postId)
    }
  }

  fun insertNote(note: Note) {
    viewModelScope.launch {
      _onLoading = true
      noteRepository.insert(note = note)
      _onLoading = false
    }
  }
}