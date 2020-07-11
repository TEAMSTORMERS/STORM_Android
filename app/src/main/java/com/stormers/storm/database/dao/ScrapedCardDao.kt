package com.stormers.storm.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.stormers.storm.database.base.BaseDao
import com.stormers.storm.database.entity.SavedCardEntity

@Dao
abstract class ScrapedCardDao : BaseDao<SavedCardEntity> {

    @Query("SELECT * FROM scraped_card_entity")
    abstract fun getAll(): List<SavedCardEntity>

    @Query("DELETE FROM scraped_card_entity")
    abstract fun deleteAll()
}