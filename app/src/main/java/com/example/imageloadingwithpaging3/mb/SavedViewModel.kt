package com.example.imageloadingwithpaging3.mb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<UnsplashPhoto>>
    private val repository: SavedRepository

    init {
        val userDao = SavedDatabase.getDatabase(
            application
        ).savedDao()
        repository = SavedRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: UnsplashPhoto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateUser(user: UnsplashPhoto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: UnsplashPhoto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun deleteAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }

}