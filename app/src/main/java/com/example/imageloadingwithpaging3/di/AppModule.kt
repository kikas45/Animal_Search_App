package com.example.imageloadingwithpaging3.di

import android.app.Application
import androidx.room.Room
import com.example.imageloadingwithpaging3.api.UnsplashApi
import com.example.imageloadingwithpaging3.mb.SavedDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UnsplashApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi =
        retrofit.create(UnsplashApi::class.java)



    @Provides
    @Singleton
    fun provideDatabase(app: Application): SavedDatabase =
        Room.databaseBuilder(app, SavedDatabase::class.java, "saved_art")
            .build()




}