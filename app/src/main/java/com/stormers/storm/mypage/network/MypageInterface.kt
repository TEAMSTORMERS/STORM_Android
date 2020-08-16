package com.stormers.storm.mypage.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MypageInterface {

    @GET("/user/mypage/{user_idx}")
    fun responseMypageData(
        @Path("user_idx") user_idx : Int) : Call<ResponseMypageData>
}