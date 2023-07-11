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

@Dao
interface SavedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UnsplashPhoto)

    @Update
    suspend fun updateUser(user: UnsplashPhoto)

    @Delete
    suspend fun deleteUser(user: UnsplashPhoto)

    @Query("DELETE FROM saved_art")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM saved_art  ORDER BY id ASC LIMIT 30")
    fun readAllData(): LiveData<List<UnsplashPhoto>>

}