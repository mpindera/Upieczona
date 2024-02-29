package com.example.upieczona.data.di

import android.app.Application
import com.example.upieczona.data.domain.repository.Repository
import com.example.upieczona.data.remote.ApiService
import com.example.upieczona.data.remote.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideApi(): ApiService {
    return Retrofit.Builder()
      .client(OkHttpClient())
      .baseUrl("http://www.upieczona.pl/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(ApiService::class.java)
  }

  @Provides
  @Singleton
  fun provideMyRepository(api: ApiService): Repository {
    return RepositoryImpl(api = api)
  }

}