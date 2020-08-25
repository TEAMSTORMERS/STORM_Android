package com.stormers.storm.card.data.source.remote

import android.util.Log
import com.stormers.storm.card.data.source.CardDataSource
import com.stormers.storm.card.model.ScrapedCardModel
import com.stormers.storm.card.model.ScrapedCardRelationModel
import com.stormers.storm.card.network.RequestCard
import com.stormers.storm.network.ResponseData
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SimpleResponse
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
                        callback.onCardLoaded(response.body()!!.data)
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
}