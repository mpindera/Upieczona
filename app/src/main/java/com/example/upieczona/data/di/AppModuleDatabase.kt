package com.example.upieczona.data.di

import android.content.Context
import androidx.room.Room
import com.example.upieczona.data.remote.repository.NoteRepositoryImpl
import com.example.upieczona.roomdatabase.NoteDao
import com.example.upieczona.roomdatabase.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleDatabase {

  @Provides
  @Singleton
  fun provideAppDatabase(@ApplicationContext appContext: Context): NoteDatabase {
    return NoteDatabase.getDatabase(appContext)
  }

  @Provides
  @Singleton
  fun provideNoteDao(database: NoteDatabase): NoteDao {
    return database.noteDao()
  }
}