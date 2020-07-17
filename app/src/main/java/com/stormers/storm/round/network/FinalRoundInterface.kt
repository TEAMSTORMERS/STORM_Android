package com.stormers.storm.round.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FinalRoundInterface {
    @GET("round/roundFinalInfo/{project_idx}")
    fun responseFinalRoundData(@Path("project_idx") projectIdx: Int) : Call<ResponseFinalRoundData>
}