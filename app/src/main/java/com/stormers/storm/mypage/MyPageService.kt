package com.stormers.storm.mypage

import com.stormers.storm.mypage.model.MyPageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageService {
    @GET("/user/mypage/{user_idx}")
    fun getMypageData(
        @Path("user_idx") userIdx : Int) : Call<MyPageResponse>
}