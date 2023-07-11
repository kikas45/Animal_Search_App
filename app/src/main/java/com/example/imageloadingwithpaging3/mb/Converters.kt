package com.example.imageloadingwithpaging3.mb

import androidx.room.TypeConverter
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhotoUrls
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashUser

class Converters {

    @TypeConverter
    fun fromSource(source: UnsplashPhotoUrls): String {
        return source.regular
    }

    @TypeConverter
    fun toSource(regular: String): UnsplashPhotoUrls {
        return UnsplashPhotoUrls(regular, regular, regular, regular, regular)
    }


    @TypeConverter
    fun fromName(source: UnsplashUser): String {
        return source.name
    }

    @TypeConverter
    fun toName(regular: String): UnsplashUser {
        return UnsplashUser(regular, regular)
    }


}