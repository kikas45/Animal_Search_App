package com.example.imageloadingwithpaging3.mb

import androidx.lifecycle.LiveData
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhoto

class SavedRepository(private val userDao: SavedDao) {

    val readAllData: LiveData<List<UnsplashPhoto>> = userDao.readAllData()

    suspend fun addUser(user: UnsplashPhoto){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: UnsplashPhoto){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: UnsplashPhoto){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }

}