package com.example.imageloadingwithpaging3.data.galaryData

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "saved_art")
data class UnsplashPhoto(
    @PrimaryKey
    val id: String,
    val description: String?,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashUser,

    // my custom items
    val custom_image: String,
    val custom_name: String

):Serializable