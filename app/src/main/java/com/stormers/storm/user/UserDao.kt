package com.stormers.storm.user

import androidx.room.Dao
import androidx.room.Query
import com.stormers.storm.base.BaseDao

@Dao
abstract class UserDao: BaseDao<UserModel> {

    @Query("SELECT * FROM user_entity WHERE user_idx = :userIdx")
    abstract fun get(userIdx: Int): UserModel?
}