package com.stormers.storm.card.data.source

import com.stormers.storm.card.model.CardMemoModel
import com.stormers.storm.card.model.RoundInfoWithCardsModel
import com.stormers.storm.card.model.ScrapedCardModel
import com.stormers.storm.card.model.ScrapedCardRelationModel


class CardRepository (
    val cardRemoteDataSource: CardDataSource,
    val cardLocalDataSource: CardDataSource) : CardDataSource {

    companion object {
        private const val TAG = "CardRepository"

        private var instance: CardRepository? = null

        @JvmStatic fun getInstance(cardRemoteDataSource: CardDataSource,
                                   cardLocalDataSource: CardDataSource
        ): CardRepository {
            return instance ?: CardRepository(cardRemoteDataSource, cardLocalDataSource)
                .apply { instance = this }
        }
    }

    override fun getScrapedCardsWithInfo(
        projectIdx: Int,
        userIdx: Int,
        callback: CardDataSource.GetCardCallback<ScrapedCardModel>
    ) {
        cardLocalDataSource.getScrapedCardsWithInfo(projectIdx, userIdx, object : CardDataSource.GetCardCallback<ScrapedCardModel> {
            override fun onCardLoaded(card: ScrapedCardModel) {
                callback.onCardLoaded(card)
            }

            override fun onDataNotAvailable() {
                getScrapedCardsFromRemote(projectIdx, userIdx, callback)
            }
        })
    }

    override fun getCardWithProjectAndRoundInfo(
        projectIdx: Int,
        roundIdx: Int,
        userIdx: Int,
        callback: CardDataSource.GetCardCallback<RoundInfoWithCardsModel>
    ) {
        cardLocalDataSource.getCardWithProjectAndRoundInfo(projectIdx, roundIdx, userIdx, object : CardDataSource.GetCardCallback<RoundInfoWithCardsModel> {
            override fun onCardLoaded(card: RoundInfoWithCardsModel) {
                callback.onCardLoaded(card)
            }

            override fun onDataNotAvailable() {
                getCardWithProjectAndRoundInfoFromRemote(projectIdx, roundIdx, userIdx, callback)
            }
        })
    }

    private fun getScrapedCardsFromRemote(
        projectIdx: Int,
        userIdx: Int,
        callback: CardDataSource.GetCardCallback<ScrapedCardModel>
    ) {
        cardRemoteDataSource.getScrapedCardsWithInfo(projectIdx, userIdx, object : CardDataSource.GetCardCallback<ScrapedCardModel> {
            override fun onCardLoaded(card: ScrapedCardModel) {
                callback.onCardLoaded(card)
                refreshLocalScrapedCards(card)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun getCardWithProjectAndRoundInfoFromRemote(
        projectIdx: Int,
        roundIdx: Int,
        userIdx: Int,
        callback: CardDataSource.GetCardCallback<RoundInfoWithCardsModel>
    ) {
        cardRemoteDataSource.getCardWithProjectAndRoundInfo(projectIdx, roundIdx, userIdx, object : CardDataSource.GetCardCallback<RoundInfoWithCardsModel> {
            override fun onCardLoaded(card: RoundInfoWithCardsModel) {
                //Todo: 로컬 디비 갱신
                callback.onCardLoaded(card)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshLocalScrapedCards(card: ScrapedCardModel) {
        cardLocalDataSource.saveScrapedCardsWithInfo(card)
    }

    override fun saveScrapedCardsWithInfo(card: ScrapedCardModel) {
        //아무것도 할 수 없음
    }

    override fun scrapCard(scrapedCardRelationModel: ScrapedCardRelationModel) {
        cardLocalDataSource.scrapCard(scrapedCardRelationModel)
        cardRemoteDataSource.scrapCard(scrapedCardRelationModel)
    }

    override fun unScrapCard(scrapedCardRelationModel: ScrapedCardRelationModel) {
        cardLocalDataSource.unScrapCard(scrapedCardRelationModel)
        cardRemoteDataSource.unScrapCard(scrapedCardRelationModel)
    }

    override fun createMemo(cardMemoModel: CardMemoModel) {
        cardLocalDataSource.createMemo(cardMemoModel)
        cardRemoteDataSource.createMemo(cardMemoModel)
    }

    override fun updateMemo(cardMemoModel: CardMemoModel) {
        cardLocalDataSource.updateMemo(cardMemoModel)
        cardRemoteDataSource.updateMemo(cardMemoModel)
    }
}