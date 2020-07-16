package com.stormers.storm.card.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestCard {
    @GET("/round/cardList/{project_idx}/{round_idx}")
    fun requestCard(@Path("project_idx") projectIdx : Int, @Path("round_idx") roundIdx: Int) : Call<ResponseCardData>
}