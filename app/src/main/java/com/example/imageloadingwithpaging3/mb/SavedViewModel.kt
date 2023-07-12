package com.example.imageloadingwithpaging3.mb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedViewModel  @Inject constructor(private val noteRepository: SavedRepository) : ViewModel() {

    val allNotes: LiveData<List<UnsplashPhoto>> = noteRepository.allNotes

    fun insert(note: UnsplashPhoto) {
        viewModelScope.launch {
            noteRepository.insert(note)
        }
    }

    fun update(note: UnsplashPhoto) {
        viewModelScope.launch {
            noteRepository.update(note)
        }
    }

    fun delete(note: UnsplashPhoto) {
        viewModelScope.launch {
            noteRepository.delete(note)
        }
    }



}
