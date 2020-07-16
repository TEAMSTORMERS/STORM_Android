package com.stormers.storm.card.dao

import androidx.room.Dao
import androidx.room.Query
import com.stormers.storm.base.BaseDao
import com.stormers.storm.card.model.SavedCardEntity

@Dao
abstract class SavedCardDao : BaseDao<SavedCardEntity> {

    @Query("SELECT * FROM scraped_card_entity")
    abstract fun getAll(): List<SavedCardEntity>

    @Query("SELECT * FROM scraped_card_entity WHERE project_idx = :projectIdx")
    abstract fun getAll(projectIdx: Int): List<SavedCardEntity>

    @Query("SELECT * FROM scraped_card_entity WHERE project_idx = :projectIdx AND round_idx = :roundIdx")
    abstract fun getAll(projectIdx: Int, roundIdx: Int): List<SavedCardEntity>

    @Query("SELECT * FROM scraped_card_entity WHERE project_idx = :projectIdx AND scraped = ${SavedCardEntity.TRUE}")
    abstract fun getAllScrapedCard(projectIdx: Int): List<SavedCardEntity>

    @Query("SELECT * FROM scraped_card_entity WHERE project_idx = :projectIdx AND round_idx = :roundIdx AND scraped = ${SavedCardEntity.TRUE}")
    abstract fun getAllScrapedCard(projectIdx: Int, roundIdx: Int): List<SavedCardEntity>

    @Query("DELETE FROM scraped_card_entity")
    abstract fun deleteAll()

    @Query("DELETE FROM scraped_card_entity WHERE project_idx = :projectIdx AND round_idx = :roundIdx")
    abstract fun deleteAll(projectIdx: Int, roundIdx: Int)
}