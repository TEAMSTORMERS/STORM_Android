package com.stormers.storm.round.network

import com.stormers.storm.network.BaseResponse
import com.stormers.storm.round.model.RoundEnterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface InterfaceRoundEnter {
    @POST("round/enter")
    fun interfaceRoundEnter(@Body body :RoundEnterModel):Call<BaseResponse>
}
