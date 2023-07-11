package com.example.imageloadingwithpaging3.mb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhoto
import com.example.imageloadingwithpaging3.data.searchData.User
import com.example.imageloadingwithpaging3.data.searchData.UserDao

@Database(entities = [UnsplashPhoto::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SavedDatabase : RoomDatabase() {

    abstract fun savedDao(): SavedDao

    companion object {
        @Volatile
        private var INSTANCE: SavedDatabase? = null

        fun getDatabase(context: Context): SavedDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SavedDatabase::class.java,
                    "saved_art"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}