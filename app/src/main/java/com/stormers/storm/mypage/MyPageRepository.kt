package com.stormers.storm.mypage

import com.stormers.storm.mypage.entity.MypageData
import retrofit2.Call


interface MyPageRepository {
    suspend fun getMyPageData(userIdx: Int): Call<ResponseMyPage>
}