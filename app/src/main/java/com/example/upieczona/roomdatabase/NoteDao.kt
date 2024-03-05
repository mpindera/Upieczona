package com.example.upieczona.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {
  @Query("SELECT * FROM Note WHERE postId = :postId")
  suspend   fun getNotesByPostId(postId: Int): List<Note>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(note: Note)
}