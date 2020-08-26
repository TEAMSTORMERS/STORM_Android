package com.stormers.storm.card.data.source.remote

import android.util.Log
import com.stormers.storm.card.data.source.CardDataSource
import com.stormers.storm.card.model.CardMemoModel
import com.stormers.storm.card.model.RoundInfoWithCardsModel
import com.stormers.storm.card.model.ScrapedCardModel
import com.stormers.storm.card.model.ScrapedCardRelationModel
import com.stormers.storm.card.network.RequestCard
import com.stormers.storm.card.network.ResponseCardData
import com.stormers.storm.network.ResponseData
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SimpleResponse
import com.stormers.storm.ui.GlobalApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CardRemoteDataSource : CardDataSource {

    private const val TAG = "CardRemoteDataSource"

    private val requestCard: RequestCard by lazy { RetrofitClient.create(RequestCard::class.java) }

    override fun getScrapedCardsWithInfo(
        projectIdx: Int,
        userIdx: Int,
        callback: CardDataSource.GetCardCallback<ScrapedCardModel>
    ) {
        Log.d(TAG, "getScrapedCards: projectIdx: $projectIdx, userIdx: $userIdx")

        requestCard.getScrapedCards(projectIdx, userIdx).enqueue(object : Callback<ResponseData<ScrapedCardModel>> {
            override fun onFailure(call: Call<ResponseData<ScrapedCardModel>>, t: Throwable) {
                Log.d(TAG, "getScrapedCard: Fail. ${t.message}")
                callback.onDataNotAvailable()
            }

            override fun onResponse(
                call: Call<ResponseData<ScrapedCardModel>>,
                response: Response<ResponseData<ScrapedCardModel>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d(TAG, "getScrapedCard: Success.")
                        if (response.body()!!.data.cardItem.isEmpty()) {
                            callback.onDataNotAvailable()
                        } else {
                            callback.onCardLoaded(response.body()!!.data)
                        }
                    } else {
                        Log.d(TAG, "getScrapedCard: Not success. ${response.body()!!.message}")
                        callback.onDataNotAvailable()
                    }
                } else {
                    Log.d(TAG, "getScrapedCard: Not successful. ${response.message()}")
                    callback.onDataNotAvailable()
                }
            }
        })
    }

    override fun saveScrapedCardsWithInfo(card: ScrapedCardModel) {
        //아무것도 할 수 없음
    }

    override fun scrapCard(scrapedCardRelationModel: ScrapedCardRelationModel) {
        requestCard.scrapCard(scrapedCardRelationModel).enqueue(object : Callback<SimpleResponse> {
            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Log.d(TAG, "scrapCard: Fail, ${t.message}")
            }

            override fun onResponse(
                call: Call<SimpleResponse>,
                response: Response<SimpleResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d(TAG, "scrapCard: Success.")
                    } else {
                        Log.d(TAG, "scrapCard: Not success, ${response.body()!!.message}")
                    }
                } else {
                    Log.d(TAG, "scrapCard: Not successful, ${response.message()}")
                }
            }
        })
    }

    override fun unScrapCard(scrapedCardRelationModel: ScrapedCardRelationModel) {
        scrapedCardRelationModel.run {
            requestCard.unScrapCard(userIdx, cardIdx)
                .enqueue(object : Callback<SimpleResponse> {
                    override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                        Log.d(TAG, "unScrapCard: Fail, ${t.message}")
                    }

                    override fun onResponse(
                        call: Call<SimpleResponse>,
                        response: Response<SimpleResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body()!!.success) {
                                Log.d(TAG, "unScrapCard: Success.")
                            } else {
                                Log.d(TAG, "unScrapCard: Not success, ${response.body()!!.message}")
                            }
                        } else {
                            Log.d(TAG, "unScrapCard: Not successful, ${response.message()}")
                        }
                    }
                })
        }
    }

    override fun getCardWithProjectAndRoundInfo(
        projectIdx: Int,
        roundIdx: Int,
        userIdx: Int,
        callback: CardDataSource.GetCardCallback<RoundInfoWithCardsModel>
    ) {
        Log.d(TAG, "requestCards: projectIdx: $projectIdx, roundIdx: $roundIdx, userIdx: $userIdx")

        requestCard.getCardWithProjectAndRoundInfo(projectIdx, roundIdx, GlobalApplication.userIdx)
            .enqueue(object : Callback<ResponseCardData> {
                override fun onFailure(call: Call<ResponseCardData>, t: Throwable) {
                    Log.d(TAG, "requestCards: fail : ${t.message}")
                    callback.onDataNotAvailable()
                }

                override fun onResponse(call: Call<ResponseCardData>, response: Response<ResponseCardData>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            Log.d(TAG, "requestCards: Success, ${response.body()!!.data.cardWithOwnerList}")
                            callback.onCardLoaded(response.body()!!.data)
                        } else {
                            Log.d(TAG, "requestCards: Not success, ${response.body()!!.message}")
                            callback.onDataNotAvailable()
                        }
                    } else {
                        Log.d(TAG, "requestCards: Not success, ${response.message()}")
                        callback.onDataNotAvailable()
                    }
                }
            })
    }

    override fun createMemo(cardMemoModel: CardMemoModel) {
        Log.d(TAG, "createMemo: $cardMemoModel")

        requestCard.createMemo(cardMemoModel).enqueue(object: Callback<SimpleResponse> {
            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Log.d(TAG, "createMemo: Fail, ${t.message}")
            }

            override fun onResponse(
                call: Call<SimpleResponse>,
                response: Response<SimpleResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d(TAG, "createMemo: Success.")
                    } else {
                        Log.d(TAG, "createMemo: Not success, ${response.body()!!.message}")
                    }
                } else {
                    Log.d(TAG, "createMemo: Not successful, ${response.message()}")
                }
            }
        })

    }

    override fun updateMemo(cardMemoModel: CardMemoModel) {
        Log.d(TAG, "updateMemo: $cardMemoModel")

        requestCard.updateMemo(cardMemoModel).enqueue(object: Callback<SimpleResponse> {
            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Log.d(TAG, "updateMemo: Fail, ${t.message}")
            }

            override fun onResponse(
                call: Call<SimpleResponse>,
                response: Response<SimpleResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d(TAG, "updateMemo: Success.")
                    } else {
                        Log.d(TAG, "updateMemo: Not success, ${response.body()!!.message}")
                    }
                } else {
                    Log.d(TAG, "updateMemo: Not successful, ${response.message()}")
                }
            }
        })
    }
}