package com.stormers.storm.card.network

import com.stormers.storm.card.model.ScrapedCardModel
import com.stormers.storm.card.model.ScrapedCardRelationModel
import com.stormers.storm.network.ResponseData
import com.stormers.storm.network.SimpleResponse
import retrofit2.Call
import retrofit2.http.*

interface RequestCard {
    @GET("/round/cardList/{project_idx}/{round_idx}/{user_idx}")
    fun requestCard(@Path("project_idx") projectIdx : Int, @Path("round_idx") roundIdx: Int,
                    @Path("user_idx") userIdx: Int) : Call<ResponseCardData>

    @GET("/card/memo/{user_idx}/{card_idx}")
    fun responseCardData(
        @Path("user_idx") user_idx : Int,
        @Path("card_idx") card_idx : Int) : Call<ResponseCardData>

    @GET("/project/finalScarpList/{user_idx}/{project_idx}")
    fun getScrapedCards(@Path("project_idx") projectIdx: Int, @Path("user_idx") userIdx: Int) : Call<ResponseData<ScrapedCardModel>>

    @POST("/card/scrap")
    fun scrapCard(@Body scrapedCardRelationModel: ScrapedCardRelationModel) : Call<SimpleResponse>

    @DELETE("/card/scrap/{user_idx}/{card_idx}")
    fun unScrapCard(@Path("user_idx") userIdx: Int, @Path("card_idx") cardIdx: Int) : Call<SimpleResponse>
}