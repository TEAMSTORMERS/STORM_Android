package com.stormers.storm.card.data.source

import com.stormers.storm.card.model.CardMemoModel
import com.stormers.storm.card.model.RoundInfoWithCardsModel
import com.stormers.storm.card.model.ScrapedCardModel
import com.stormers.storm.card.model.ScrapedCardRelationModel

interface CardDataSource {

    interface LoadCardsCallback<T> {

        fun onCardsLoaded(cards: List<T>)

        fun onDataNotAvailable()
    }

    interface GetCardCallback<T> {

        fun onCardLoaded(card: T)

        fun onDataNotAvailable()
    }

    fun getScrapedCardsWithInfo(projectIdx: Int, userIdx: Int, callback: GetCardCallback<ScrapedCardModel>)

    fun saveScrapedCardsWithInfo(card: ScrapedCardModel)

    fun scrapCard(scrapedCardRelationModel: ScrapedCardRelationModel)

    fun unScrapCard(scrapedCardRelationModel: ScrapedCardRelationModel)

    fun getCardWithProjectAndRoundInfo(projectIdx: Int, roundIdx: Int, userIdx: Int, callback: GetCardCallback<RoundInfoWithCardsModel>)

    fun createMemo(cardMemoModel: CardMemoModel)

    fun updateMemo(cardMemoModel: CardMemoModel)
}