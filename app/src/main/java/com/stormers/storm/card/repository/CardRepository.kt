package com.stormers.storm.card.repository

import android.util.Log
import com.stormers.storm.card.CardType
import com.stormers.storm.card.dao.CardDao
import com.stormers.storm.card.model.CardEnumModel
import com.stormers.storm.card.model.CardEntity
import com.stormers.storm.ui.GlobalApplication

class CardRepository {

    companion object {
        private const val TAG = "CardRepository"

        private var instance: CardRepository? = null

        fun getInstance(): CardRepository {
            if (instance == null) {
                synchronized(CardRepository::class.java) {
                    if (instance == null) {
                        instance = CardRepository()
                    }
                }
            }
            return instance!!
        }
    }

    private val dao: CardDao by lazy { GlobalApplication.databaseManager.savedCardDao() }

    fun getAll(projectIdx: Int): List<CardEntity>? {
        return dao.getAll(projectIdx)
    }

    fun getAll(projectIdx: Int, roundIdx: Int): List<CardEntity>? {
        val result = dao.getAll(projectIdx, roundIdx)
        Log.d(TAG, "getAll: projectIdx: $projectIdx, roundIdx: $roundIdx, result : $result")
        return result
    }

    fun getContentsAll(projectIdx: Int, limit: Int): List<String> {
        var result = dao.getContentsAll(projectIdx, limit)
        Log.d(TAG, "getContentsAll: projectIdx: $projectIdx, numberOfCounts: $limit result : $result")
        if (result == null) {
            result = mutableListOf()
        }
        return result
    }

    fun getAllForList(projectIdx: Int, roundIdx: Int, callback: LoadEnumCardsCallback) {
        val results = getAll(projectIdx, roundIdx)

        if (results == null) {
            callback.onDataNotAvailable()
        } else {
            callback.onCardLoaded(toEnumModels(results))
        }
    }

    fun getScrapAllForList(projectIdx: Int, callback: LoadEnumCardsCallback) {
        val results = getAllScrapedCard(projectIdx)

        if (results == null || results.isEmpty()) {
            callback.onDataNotAvailable()
        } else {
            callback.onCardLoaded(toEnumModels(results))
        }
    }

    fun getAllScrapedCard(projectIdx: Int): List<CardEntity>? {
        return dao.getAllScrapedCard(projectIdx)
    }

    fun getAllScrapedCard(projectIdx: Int, roundIdx: Int): List<CardEntity>? {
        return dao.getAllScrapedCard(projectIdx, roundIdx)
    }

    fun get(cardIdx: Int): CardEntity? {
        val result = dao.get(cardIdx)
        Log.d(TAG, "cardIdx: $cardIdx, get: $result")

        return result
    }

    fun delete(projectIdx: Int, roundIdx: Int) {
        dao.deleteAll(projectIdx, roundIdx)
    }

    fun insert(entity: CardEntity) {
        dao.insert(entity)
    }

    fun update(entity: CardEntity) {
        dao.update(entity)
    }

    fun update(cardEnumModel: CardEnumModel) {

        cardEnumModel.let {
            val previousCard = get(it.cardIdx)
            if (previousCard == null) {
                Log.e(TAG, "update: No card in db. cardIdx(${it.cardIdx})")
                return
            }

            val isScraped = if (it.isScraped) {
                CardEntity.TRUE
            } else {
                CardEntity.FALSE
            }
            previousCard.isScraped = isScraped
            dao.update(previousCard)
            Log.d(TAG, "update: isScraped == ${!it.isScraped} --> ${it.isScraped} cardIdx(${it.cardIdx})")
        }
    }

    private fun toEnumModels(cardEntities: List<CardEntity>): List<CardEnumModel> {
        return List(cardEntities.size) { i ->
            val isScraped = CardType.scrapConverter(cardEntities[i].isScraped)
            val cardType = CardType.typeConverter(cardEntities[i].cardType)
            CardEnumModel(cardEntities[i].cardIdx, cardEntities[i].projectIdx, cardEntities[i].roundIdx, isScraped, cardType, cardEntities[i].content)
        }
    }

    interface LoadEnumCardsCallback {
        fun onCardLoaded(cards: List<CardEnumModel>)

        fun onDataNotAvailable()
    }
}