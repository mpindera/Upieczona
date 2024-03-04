package com.example.upieczona.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
  @Query("SELECT * FROM Note WHERE postId = :postId")
  fun getNotesByPostId(postId: Int): List<Note>

  @Insert
  fun insert(note: Note)
}