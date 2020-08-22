package com.stormers.storm.card.repository

import android.util.Log
import com.stormers.storm.card.CardType
import com.stormers.storm.card.dao.CardDao
import com.stormers.storm.card.model.CardEnumModel
import com.stormers.storm.card.model.CardEntity
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.user.UserRepository

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

    private val userRepository by lazy { UserRepository.getInstance() }
    

    fun getAll(roundIdx: Int): List<CardEntity> {
        val result = dao.getAll(roundIdx)
        Log.d(TAG, "getAll: roundIdx: $roundIdx, result : $result")
        return result
    }
    
    fun getAll(roundIdx: Int, callback: LoadCardModel<CardModel>) {
        val result = dao.getAll(roundIdx)
        val cardModels = toCardModel(result)

        if (cardModels == null || cardModels.isEmpty()) {
            callback.onDataNotAvailable()
        } else {
            callback.onCardsLoaded(cardModels)
        }
    }

    fun getContentsAll(projectIdx: Int, limit: Int): List<String> {
        var result = dao.getContentsAll(projectIdx, limit)
        Log.d(TAG, "getContentsAll: projectIdx: $projectIdx, numberOfCounts: $limit result : $result")
        if (result == null) {
            result = mutableListOf()
        }
        return result
    }

    fun getAllForList(roundIdx: Int, callback: LoadCardModel<CardEnumModel>) {
        val results = getAll(roundIdx)

        if (results.isEmpty()) {
            callback.onDataNotAvailable()
        } else {
            callback.onCardsLoaded(toEnumModels(results))
        }
    }

    fun getScrapAllForList(projectIdx: Int, callback: LoadCardModel<CardEnumModel>) {
        val results = getScrapAll(projectIdx)

        if (results.isEmpty()) {
            callback.onDataNotAvailable()
        } else {
            callback.onCardsLoaded(toEnumModels(results))
        }
    }

    fun getScrapAll(projectIdx: Int): List<CardEntity> {
        return dao.getAllScrapedCard(projectIdx)
    }

    fun getScrapAll(projectIdx: Int, callback: LoadCardModel<CardModel>) {
        val results = getScrapAll(projectIdx)
        val cardModels = toCardModel(results)

        if (cardModels == null || cardModels.isEmpty()) {
            callback.onDataNotAvailable()
        } else {
            callback.onCardsLoaded(cardModels)
        }
    }

    fun get(cardIdx: Int): CardEntity? {
        val result = dao.get(cardIdx)
        Log.d(TAG, "cardIdx: $cardIdx, get: $result")

        return result
    }

    fun get(cardIdx: Int, callback: GetCardModel<CardEntity>) {
        val result = get(cardIdx)

        if (result == null) {
            callback.onDataNotAvailable()
        } else {
            callback.onCardLoaded(result)
        }
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

    fun update(cardModel: CardModel) {
        cardModel.let {
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

            previousCard.memo = it.cardMemo

            dao.update(previousCard)
        }

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

    private fun toCardModel(cardEntities: List<CardEntity>): List<CardModel>? {
        return List(cardEntities.size) { i ->
            cardEntities[i].let {
                val isScraped = CardType.scrapConverter(it.isScraped)
                val cardType = CardType.typeConverter(it.cardType)
                val owner = userRepository.get(it.userIdx)

                if (owner == null) {
                    Log.e(TAG, "getAll: Wrong card. no owner(${it.userIdx}))")
                    return null
                }
                CardModel(it.cardIdx, isScraped, cardType, it.content, it.memo, owner)
            }
        }
    }
    
    interface LoadCardModel<T> {
        fun onCardsLoaded(cards: List<T>)
        
        fun onDataNotAvailable()
    }

    interface GetCardModel<T> {
        fun onCardLoaded(card: T)

        fun onDataNotAvailable()
    }
}