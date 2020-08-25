package com.stormers.storm.util

import com.stormers.storm.project.data.Project
import com.stormers.storm.round.data.Round
import com.stormers.storm.user.UserDao

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