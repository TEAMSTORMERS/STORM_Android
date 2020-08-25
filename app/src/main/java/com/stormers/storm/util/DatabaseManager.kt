package com.stormers.storm.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stormers.storm.card.data.source.local.CardDao
import com.stormers.storm.project.data.source.local.ProjectDao
import com.stormers.storm.project.ProjectParticipantDao
import com.stormers.storm.project.data.Project
import com.stormers.storm.project.data.ProjectParticipant
import com.stormers.storm.round.data.source.local.RoundDao
import com.stormers.storm.round.data.Round
import com.stormers.storm.user.UserDao
import com.stormers.storm.user.User

//@Database(entities = [Card::class, Round::class, Project::class, User::class,
//RoundParticipant::class, ProjectParticipant::class], version = 1)
//abstract class DatabaseManager : RoomDatabase() {
//    abstract fun savedCardDao() : CardDao
//    abstract fun roundDao() : RoundDao
//    abstract fun userDao() : UserDao
//    abstract fun roundParticipantDao(): RoundParticipantDao
//    abstract fun projectDao(): ProjectDao
//    abstract fun projectParticipantDao(): ProjectParticipantDao
//
//    companion object {
//        private const val DB_NAME = "storm-db"
//        private var instance: DatabaseManager? = null
//
//        fun getInstance(context: Context): DatabaseManager {
//            return instance ?: synchronized(this) {
//                instance
//                    ?: buildDatabase(
//                        context
//                    )
//            }
//        }
//
//        private fun buildDatabase(context: Context): DatabaseManager {
//            return Room.databaseBuilder(context.applicationContext, DatabaseManager::class.java,
//                DB_NAME
//            )
//                .allowMainThreadQueries()
//                .build()
//        }
//    }
//}