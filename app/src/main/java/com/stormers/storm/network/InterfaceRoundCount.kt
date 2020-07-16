package com.stormers.storm.network

import com.stormers.storm.round.model.ResponseRoundCountModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceRoundCount{
    @GET("/round/count/{project_idx}")
    fun getRoundCount(@Path ("project_idx")projectIdx : String) : Call<ResponseRoundCountModel>
}