package com.stormers.storm.user

import androidx.room.Dao
import androidx.room.Query
import com.stormers.storm.base.BaseDao

@Dao
abstract class UserDao: BaseDao<User> {

    @Query("SELECT * FROM user_entity WHERE user_idx = :userIdx")
    abstract fun get(userIdx: Int): User?

    @Query("SELECT user_img FROM user_entity Join project_participant_entity WHERE project_idx = :projectIdx")
    abstract fun getParticipants(projectIdx: Int): List<String>
}