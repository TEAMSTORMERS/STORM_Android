package com.stormers.storm.mypage

import retrofit2.Call


interface MyPageRepository {
    suspend fun getMyPageData(userIdx: Int): Call<ResponseMyPage>
}