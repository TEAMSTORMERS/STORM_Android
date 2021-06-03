package com.stormers.storm.mypage

import com.stormers.storm.mypage.network.ResponseMypageData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MypageService {

    @GET("/user/mypage/{user_idx}")
    suspend fun getMypageData(
        @Path("user_idx") user_idx : Int) : Call<ResponseMyPage>
}