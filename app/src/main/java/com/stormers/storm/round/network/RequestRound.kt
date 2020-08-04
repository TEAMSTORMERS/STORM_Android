package com.stormers.storm.round.network

import com.stormers.storm.network.BaseResponse
import com.stormers.storm.network.SimpleResponse
import com.stormers.storm.project.network.response.ResponseProjectUserListModel
import com.stormers.storm.round.network.response.ResponseFinalRoundData
import com.stormers.storm.round.network.response.ResponseRoundCountModel
import com.stormers.storm.round.model.RoundEnterModel
import com.stormers.storm.round.model.RoundSettingModel
import com.stormers.storm.round.network.response.ResponseRoundInfoModel
import com.stormers.storm.round.network.response.ResponseRoundUserImageModel
import retrofit2.Call
import retrofit2.http.*

interface RequestRound {
    @POST("round/enter")
    fun interfaceRoundEnter(@Body body :RoundEnterModel):Call<SimpleResponse>

    @GET("round/memberList/{round_idx}")
    fun showRoundUser(@Path("round_idx") RoundIdx : Int) : Call<ResponseProjectUserListModel>

    @DELETE("round/leave")
    fun roundExit(
        @Field("user_idx") userIdx : Int,
        @Field("round_idx") roundIdx : Int): Call<BaseResponse>

    @GET("/round/count/{project_idx}")
    fun responseRoundCount(@Path("project_idx") projectIdx :Int): Call<ResponseRoundCountModel>

    @GET("round/roundFinalInfo/{project_idx}")
    fun responseFinalRoundData(@Path("project_idx") projectIdx: Int) : Call<ResponseFinalRoundData>

    @POST("round/setting")
    fun roundSetting(@Body body: RoundSettingModel) : Call<BaseResponse>

    @GET("round/info/{project_idx}")
    fun responseRoundInfo(@Path("project_idx") projectIdx :Int) :Call<ResponseRoundInfoModel>

    @GET("/round/count/{project_idx}")
    fun getRoundCount(@Path ("project_idx")projectIdx : String) : Call<ResponseRoundCountModel>

    @GET("round/roundFinalInfo/{project_idx}")
    fun getRoundUserImage(@Path("project_idx") projectIdx: Int) : Call<ResponseRoundUserImageModel>
}
