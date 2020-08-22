package com.stormers.storm.card.dao

import androidx.room.Dao
import androidx.room.Query
import com.stormers.storm.base.BaseDao
import com.stormers.storm.card.model.CardEntity

@Dao
abstract class CardDao : BaseDao<CardEntity> {

    @Query("SELECT * FROM scraped_card_entity")
    abstract fun getAll(): List<CardEntity>?

    @Query("SELECT * FROM scraped_card_entity WHERE project_idx = :projectIdx AND round_idx = :roundIdx")
    abstract fun getAll(projectIdx: Int, roundIdx: Int): List<CardEntity>?

    @Query("SELECT * FROM scraped_card_entity WHERE round_idx = :roundIdx")
    abstract fun getAll(roundIdx: Int): List<CardEntity>

    @Query("SELECT * FROM scraped_card_entity WHERE project_idx = :projectIdx AND scraped = ${CardEntity.TRUE}")
    abstract fun getAllScrapedCard(projectIdx: Int): List<CardEntity>

    @Query("SELECT * FROM scraped_card_entity WHERE card_idx = :cardIdx")
    abstract fun get(cardIdx: Int): CardEntity?

    @Query("SELECT * FROM scraped_card_entity WHERE project_idx = :projectIdx AND round_idx = :roundIdx AND scraped = ${CardEntity.TRUE}")
    abstract fun getAllScrapedCard(projectIdx: Int, roundIdx: Int): List<CardEntity>?

    @Query("SELECT content FROM scraped_card_entity WHERE project_idx = :projectIdx LIMIT :limit")
    abstract fun getContentsAll(projectIdx: Int, limit: Int): List<String>?

    @Query("DELETE FROM scraped_card_entity")
    abstract fun deleteAll()

    @Query("DELETE FROM scraped_card_entity WHERE project_idx = :projectIdx AND round_idx = :roundIdx")
    abstract fun deleteAll(projectIdx: Int, roundIdx: Int)
}