package com.stormers.storm.mypage

import com.stormers.storm.mypage.entity.MypageData
import retrofit2.Call
import javax.inject.Inject

class MyPageDataSource @Inject constructor(
    private val myPageService: MypageService
){
    suspend fun getMyPageData(userIdx: Int): Call<ResponseMyPage> = myPageService.getMypageData(userIdx)
}