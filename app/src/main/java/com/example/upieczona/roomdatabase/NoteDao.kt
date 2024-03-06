package com.example.upieczona.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
  @Query("SELECT * FROM Note WHERE postId = :postId")
  suspend fun getNotesByPostIdFromDatabase(postId: Int): List<Note>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNoteToDatabase(note: Note)

  @Query("DELETE FROM Note WHERE id = :id")
  suspend fun deleteNoteFromDatabase(id: Int)

  @Update
  suspend fun updateNoteFromDatabase(note: Note)
}