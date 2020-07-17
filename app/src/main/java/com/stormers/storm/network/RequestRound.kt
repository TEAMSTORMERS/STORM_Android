package com.stormers.storm.network

import com.stormers.storm.round.network.response.ResponseRoundCountModel
import com.stormers.storm.round.network.response.ResponseRoundInfoModel
import com.stormers.storm.round.model.RoundSettingModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface RequestRound {
    @POST("round/setting")
    fun roundSetting(@Body body: RoundSettingModel) : Call<BaseResponse>

    @GET("round/info/{project_idx}")
    fun responseRoundInfo(@Path("project_idx") projectIdx :Int) :Call<ResponseRoundInfoModel>

    @GET("/round/count/{project_idx}")
    fun getRoundCount(@Path ("project_idx")projectIdx : String) : Call<ResponseRoundCountModel>
}