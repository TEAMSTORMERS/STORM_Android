package com.stormers.storm.network

import com.stormers.storm.round.model.ResponseRoundInfoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceRoundInfo{
    @GET("round/info/{project_idx}")
    fun responseRoundInfo(@Path("project_idx") projectIdx :Int) :Call<ResponseRoundInfoModel>
}