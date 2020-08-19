package com.stormers.storm.mypage.network

import com.stormers.storm.network.SimpleResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MypageInterface {

    @GET("/user/mypage/{user_idx}")
    fun responseMypageData(
        @Path("user_idx") user_idx : Int) : Call<ResponseMypageData>

    @FormUrlEncoded
    @PUT("/user/mypage/name")
    fun updateMypageName(
        @Field("user_idx") user_idx : Int,
        @Field("user_name") user_name : String) : Call<SimpleResponse>
}