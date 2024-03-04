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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
  private val noteRepository: NoteRepositoryImpl
) : ViewModel() {

  private val _notes = mutableStateOf<List<Note>>(emptyList())
  val notes: State<List<Note>> = _notes

  private var _onLoading by mutableStateOf(false)
  val onLoading: Boolean
    get() = _onLoading


  private fun loadTask(postId: Int) {
    viewModelScope.launch() {
      _notes.value = noteRepository.getAllNote(postId = postId)
    }
  }
  
  suspend fun insertTask(note: Note, postId: Int) {
    viewModelScope.launch {
      _onLoading = true
      noteRepository.insertTask(note)
      _notes.value = noteRepository.getAllNote(postId = postId)
      _onLoading = false
    }
  }

}