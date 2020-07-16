package com.stormers.storm.project.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CardInterface {

    @GET("/card/memo/{user_idx}/{card_idx}")
    fun responseCardData(
        @Path("user_idx") user_idx : Int,
        @Path("card_idx") card_idx : Int) : Call<ResponseCardData>
}