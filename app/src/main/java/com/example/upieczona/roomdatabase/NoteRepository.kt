package com.example.upieczona.roomdatabase

interface NoteRepository {

  suspend fun insert(note: Note)
  suspend fun getNotesByPostId(postId: Int): List<Note>

}