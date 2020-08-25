package com.stormers.storm.card.data.source.local

import androidx.room.Dao
import androidx.room.Query
import com.stormers.storm.base.BaseDao
import com.stormers.storm.card.data.Card
import com.stormers.storm.card.model.CardPreviewModel

@Dao
abstract class CardDao : BaseDao<Card> {

//    @Query("SELECT * FROM card_entity")
//    abstract fun getAll(): List<Card>?
//
//    @Query("SELECT * FROM card_entity WHERE project_idx = :projectIdx AND round_idx = :roundIdx")
//    abstract fun getAll(projectIdx: Int, roundIdx: Int): List<Card>?
//
//    @Query("SELECT * FROM card_entity WHERE round_idx = :roundIdx")
//    abstract fun getAll(roundIdx: Int): List<Card>
//
////    @Query("SELECT * FROM scraped_card_entity WHERE project_idx = :projectIdx AND scraped = ${CardEntity.TRUE}")
////    abstract fun getAllScrapedCard(projectIdx: Int): List<CardEntity>
//
//    @Query("SELECT * FROM card_entity WHERE card_idx = :cardIdx")
//    abstract fun get(cardIdx: Int): Card?
//
////    @Query("SELECT * FROM scraped_card_entity WHERE project_idx = :projectIdx AND round_idx = :roundIdx AND scraped = ${CardEntity.TRUE}")
////    abstract fun getAllScrapedCard(projectIdx: Int, roundIdx: Int): List<CardEntity>?
//
//    @Query("SELECT card_idx, card_img, card_txt FROM card_entity WHERE project_idx = :projectIdx LIMIT :limit")
//    abstract fun getCardPreviews(projectIdx: Int, limit: Int): List<CardPreviewModel>
//
//    @Query("SELECT card_entity.card_idx, card_entity.card_img, card_entity.card_txt FROM project_entity JOIN card_entity ON project_entity.project_idx = card_entity.project_idx JOIN scrap ON scrap.card_idx = card_entity.card_idx WHERE project_entity.project_idx = :projectIdx AND scrap.user_idx = :userIdx")
//    abstract fun getScrapedCard(projectIdx: Int, userIdx: Int) : List<CardPreviewModel>
//
//    @Query("DELETE FROM card_entity")
//    abstract fun deleteAll()
//
//    @Query("DELETE FROM card_entity WHERE project_idx = :projectIdx AND round_idx = :roundIdx")
//    abstract fun deleteAll(projectIdx: Int, roundIdx: Int)
}