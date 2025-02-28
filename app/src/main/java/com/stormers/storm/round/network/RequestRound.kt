package com.stormers.storm.round.network

import com.stormers.storm.network.BaseResponse
import com.stormers.storm.network.SimpleResponse
import com.stormers.storm.project.network.response.ResponseParticipant
import com.stormers.storm.round.network.response.ResponseFinalRoundData
import com.stormers.storm.round.network.response.ResponseRoundCountModel
import com.stormers.storm.round.model.RoundEnterModel
import com.stormers.storm.round.model.RoundSettingModel
import com.stormers.storm.round.network.response.ResponseRoundInfoModel
import retrofit2.Call
import retrofit2.http.*

interface RequestRound {
    @POST("round/enter")
    fun enterNextRound(@Body body :RoundEnterModel) : Call<BaseResponse>

    @GET("round/memberList/{project_idx}/{round_idx}")
    fun showRoundUser(@Path("project_idx") projectIdx: Int,
                      @Path("round_idx") roundIdx : Int) : Call<ResponseParticipant>

    @DELETE("round/leave")
    fun roundExit(
        @Field("user_idx") userIdx : Int,
        @Field("round_idx") roundIdx : Int): Call<BaseResponse>

    @GET("/round/count/{project_idx}")
    fun responseRoundCount(@Path("project_idx") projectIdx :Int): Call<ResponseRoundCountModel>

    @GET("round/roundFinalInfo/{user_idx}/{project_idx}")
    fun getRoundInfo(@Path("project_idx") projectIdx: Int, @Path("user_idx") userIdx: Int) : Call<ResponseFinalRoundData>

    @POST("round/setting")
    fun roundSetting(@Body body: RoundSettingModel) : Call<BaseResponse>

    @GET("round/info/{project_idx}")
    fun responseRoundInfo(@Path("project_idx") projectIdx :Int) :Call<ResponseRoundInfoModel>

    @GET("/round/count/{project_idx}")
    fun getRoundCount(@Path ("project_idx")projectIdx : String) : Call<ResponseRoundCountModel>

    @DELETE("round/leave/{user_idx}/{project_idx}/{round_idx}")
    fun exitRound(@Path("user_idx") userIdx: Int, @Path("project_idx") projectIdx: Int,
                  @Path("round_idx") roundIdx: Int) : Call<SimpleResponse>

}
