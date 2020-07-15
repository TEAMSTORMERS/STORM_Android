package com.stormers.storm.network

import com.stormers.storm.project.model.JoinProjectUsingCodeModel
import com.stormers.storm.round.model.RoundSettingModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface InterfaceRoundSetting{
    @POST("round/setting")
    fun roundSetting(@Body body: RoundSettingModel) : Call<BaseResponse>
}