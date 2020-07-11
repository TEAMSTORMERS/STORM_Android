package com.stormers.storm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stormers.storm.database.dao.ScrapedCardDao
import com.stormers.storm.database.entity.SavedCardEntity

@Database(entities = [SavedCardEntity::class], version = 1)
abstract class DatabaseManager : RoomDatabase() {
    abstract fun scrapedCardDao() : ScrapedCardDao

    companion object {
        private const val DB_NAME = "storm-db"
        private var instance: DatabaseManager? = null

        fun getInstance(context: Context): DatabaseManager {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context): DatabaseManager {
            return Room.databaseBuilder(context.applicationContext, DatabaseManager::class.java, DB_NAME)
                .allowMainThreadQueries()
                .build()
        }
    }
}