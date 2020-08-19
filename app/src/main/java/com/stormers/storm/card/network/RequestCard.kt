package com.stormers.storm.card.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestCard {
    @GET("/round/cardList/{project_idx}/{round_idx}/{user_idx}")
    fun requestCard(@Path("project_idx") projectIdx : Int, @Path("round_idx") roundIdx: Int,
                    @Path("user_idx") userIdx: Int) : Call<ResponseCardData>

    @GET("/card/memo/{user_idx}/{card_idx}")
    fun responseCardData(
        @Path("user_idx") user_idx : Int,
        @Path("card_idx") card_idx : Int) : Call<ResponseCardData>
}