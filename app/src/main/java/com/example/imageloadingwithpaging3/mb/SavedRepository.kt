package com.example.imageloadingwithpaging3.mb

import androidx.lifecycle.LiveData
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhoto
import javax.inject.Inject

class SavedRepository @Inject constructor( private  val savedDatabase: SavedDatabase) {

    //  val allNotes: LiveData<List<UnsplashPhoto>> = savedDatabase.noteDao().getAllNotes()

    val allNotes = savedDatabase.noteDao().getAllNotes()

        suspend fun insert(note: UnsplashPhoto) {
            savedDatabase.noteDao().insert(note)
        }

        suspend fun update(note: UnsplashPhoto) {
            savedDatabase.noteDao().update(note)
        }

        suspend fun delete(note: UnsplashPhoto) {
            savedDatabase.noteDao().delete(note)
        }


    }

