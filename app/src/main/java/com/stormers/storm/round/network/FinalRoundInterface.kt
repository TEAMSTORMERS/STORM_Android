package com.stormers.storm.round.network

import com.stormers.storm.project.network.ResponseProjectData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FinalRoundInterface {
    @GET("round/roundFinalInfo/{project_idx}")
    fun responseFinalRoundData(@Path("project_idx") projectIdx : String) : Call<ResponseFinalRoundData>
}