package com.stormers.storm.round.network

import com.stormers.storm.project.model.ResponseProjectUserListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceRoundUser {
    @GET("round/memberList/{round_idx}")
    fun showRoundUser(@Path("round_idx") RoundIdx : Int) : Call<ResponseProjectUserListModel>

}