package com.example.upieczona.data.di

import android.app.Application
import androidx.room.Room
import com.example.upieczona.data.remote.repository.NoteRepositoryImpl
import com.example.upieczona.roomdatabase.NoteDao
import com.example.upieczona.roomdatabase.NoteDatabase
import com.example.upieczona.data.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleDatabase {
  @Provides
  @Singleton
  fun provideMyDataBase(app: Application): NoteDatabase {
    return Room.databaseBuilder(
      app,
      NoteDatabase::class.java,
      "MyDataBase"
    ).build()
  }

  @Provides
  @Singleton
  fun provideNoteDao(myDatabase: NoteDatabase): NoteDao {
    return myDatabase.noteDao
  }

  @Provides
  @Singleton
  fun provideMyRepository(noteDao: NoteDao): NoteRepository {
    return NoteRepositoryImpl(noteDao)
  }
}
