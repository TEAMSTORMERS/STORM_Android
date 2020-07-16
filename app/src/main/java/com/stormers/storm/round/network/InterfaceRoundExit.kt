package com.stormers.storm.round.network

import com.stormers.storm.network.BaseResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.Path

interface InterfaceRoundExit{
    @DELETE("round/leave")
    fun roundExit(
        @Field("user_idx") userIdx : Int,
        @Field("round_idx") roundIdx : Int): Call<BaseResponse>
}