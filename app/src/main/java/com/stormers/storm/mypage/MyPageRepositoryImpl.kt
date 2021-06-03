package com.stormers.storm.mypage

import com.stormers.storm.mypage.entity.MypageData
import retrofit2.Call
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val datasource: MyPageDataSource
): MyPageRepository {
    override suspend fun getMyPageData(userIdx: Int): Call<ResponseMyPage> = datasource.getMyPageData(userIdx)
}