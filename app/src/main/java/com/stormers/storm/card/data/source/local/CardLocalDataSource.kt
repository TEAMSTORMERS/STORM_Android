package com.stormers.storm.card.data.source.local

import com.stormers.storm.card.data.source.CardDataSource
import com.stormers.storm.card.data.source.CardRepository
import com.stormers.storm.card.model.RoundInfoWithCardsModel
import com.stormers.storm.card.model.ScrapCardWithRoundInfo
import com.stormers.storm.card.model.ScrapedCardModel
import com.stormers.storm.card.model.ScrapedCardRelationModel
//import com.stormers.storm.project.data.source.local.ProjectDao
import com.stormers.storm.round.data.source.local.RoundDao
//import com.stormers.storm.util.AppExecutors

class CardLocalDataSource private constructor(
//    val appExecutors: AppExecutors,
//    val projectDao: ProjectDao,
//    val roundDao: RoundDao,
//    val cardDao: CardDao
): CardDataSource {

    companion object {
        private const val TAG = "CardLocalDataSource"

        private var instance: CardLocalDataSource? = null

        @JvmStatic fun getInstance(/*appExecutors: AppExecutors, cardDao: CardDao*/): CardLocalDataSource {
            return instance ?: CardLocalDataSource(/*appExecutors, cardDao*/)
                .apply { instance = this }
        }
    }

    override fun getScrapedCardsWithInfo(
        projectIdx: Int,
        userIdx: Int,
        callback: CardDataSource.GetCardCallback<ScrapedCardModel>
    ) {
//        appExecutors.diskIO.execute {
//            val projectName = projectDao.getProjectNameOfScrapedCard(projectIdx, userIdx)
//            val scrapedCards = cardDao.getScrapedCard(projectIdx, userIdx)
//            val scrapedCardInfo = mutableListOf<ScrapCardWithRoundInfo>()
//
//            appExecutors.mainThread.execute {
//                if (projectName == null) {
//                    callback.onDataNotAvailable()
//                }
//            }
//
//            if (projectName == null) {
//                return@execute
//            }
//
//            for (i in 0 until projectName.scrapCount) {
//                val roundInfo = roundDao.getRoundInfoOfScrapedCard(scrapedCards[i].cardIdx)
//
//                scrapedCardInfo.add(
//                    ScrapCardWithRoundInfo(
//                    roundInfo.roundNumber,
//                    roundInfo.roundPurpose,
//                    roundInfo.roundTime,
//                    scrapedCards[i].cardIdx,
//                    scrapedCards[i].cardImage!!,
//                    scrapedCards[i].cardText!!)
//                )
//            }
//
//            val result = ScrapedCardModel(projectName.projectName, projectName.scrapCount, scrapedCardInfo)
//
//            appExecutors.mainThread.execute {
//                callback.onCardLoaded(result)
//            }
//        }
        callback.onDataNotAvailable()
    }

    override fun saveScrapedCardsWithInfo(card: ScrapedCardModel) {
        //Todo: DB에 저장할 수 있도록 서버와 맞추기
    }

    override fun scrapCard(scrapedCardRelationModel: ScrapedCardRelationModel) {
        //Todo: DB에 저장할 수 있도록 서버와 맞추기
    }

    override fun unScrapCard(scrapedCardRelationModel: ScrapedCardRelationModel) {
        //Todo: DB에 저장할 수 있도록 서버와 맞추기
    }

    override fun getCardWithProjectAndRoundInfo(
        projectIdx: Int,
        roundIdx: Int,
        userIdx: Int,
        callback: CardDataSource.GetCardCallback<RoundInfoWithCardsModel>
    ) {
        callback.onDataNotAvailable()
    }
}