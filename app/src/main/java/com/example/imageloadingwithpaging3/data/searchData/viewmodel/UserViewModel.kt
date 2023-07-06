package com.example.imageloadingwithpaging3.data.searchData.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.imageloadingwithpaging3.data.searchData.User
import com.example.imageloadingwithpaging3.data.searchData.UserDatabase
import com.example.imageloadingwithpaging3.data.searchData.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(
            application
        ).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun deleteAllUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }

}