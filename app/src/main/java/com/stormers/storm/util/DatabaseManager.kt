package com.stormers.storm.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stormers.storm.card.dao.SavedCardDao
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.project.model.ProjectEntity
import com.stormers.storm.round.RoundDao
import com.stormers.storm.round.model.RoundEntity
import com.stormers.storm.roundparticipant.RoundParticipantDao
import com.stormers.storm.roundparticipant.RoundParticipantEntity
import com.stormers.storm.user.UserDao
import com.stormers.storm.user.UserModel

@Database(entities = [SavedCardEntity::class, RoundEntity::class, ProjectEntity::class, UserModel::class,
RoundParticipantEntity::class], version = 1)
abstract class DatabaseManager : RoomDatabase() {
    abstract fun savedCardDao() : SavedCardDao
    abstract fun roundDao() : RoundDao
    abstract fun userDao() : UserDao
    abstract fun roundParticipantDao(): RoundParticipantDao

    companion object {
        private const val DB_NAME = "storm-db"
        private var instance: DatabaseManager? = null

        fun getInstance(context: Context): DatabaseManager {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    )
            }
        }

        private fun buildDatabase(context: Context): DatabaseManager {
            return Room.databaseBuilder(context.applicationContext, DatabaseManager::class.java,
                DB_NAME
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}