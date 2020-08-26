package com.stormers.storm.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: T)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: List<T>)

    @Insert
    fun insert(vararg obj: T)

    @Update
    fun update(entity: T)

    @Delete
    fun delete(entity: T)
}