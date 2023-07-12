package com.example.imageloadingwithpaging3.mb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhoto
import com.example.imageloadingwithpaging3.data.searchData.User
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: UnsplashPhoto)

    @Update
    suspend fun update(note: UnsplashPhoto)

    @Delete
    suspend fun delete(note: UnsplashPhoto)



    @Query("SELECT * FROM saved_art  ORDER BY id ASC LIMIT 30")
    fun getAllNotes(): LiveData<List<UnsplashPhoto>>



}