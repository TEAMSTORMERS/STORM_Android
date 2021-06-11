package com.stormers.storm.mypage.datasource

import com.stormers.storm.mypage.model.ResponseMyPage
import okhttp3.ResponseBody

interface MyPageDataSource {
    interface MyPageCallback{
        fun onSuccessFetchUserData(responseMyPage: ResponseMyPage)
        fun onFailedFetchUserData(networkError: Boolean, errorBody: ResponseBody?)
    }

    fun requestFetchUserData(userIdx: Int, callback: MyPageCallback)
}