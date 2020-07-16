package com.stormers.storm.card.network

import com.stormers.storm.round.model.ResponseRoundCountModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceRoundCount {
    @GET("/round/count/{project_idx}")
    fun responseRoundCount(@Path("project_idx") projectIdx :Int): Call<ResponseRoundCountModel>
}