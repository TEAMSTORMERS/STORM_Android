package com.stormers.storm.mypage.datasource

import com.stormers.storm.mypage.model.MyPageResponse
import okhttp3.ResponseBody

interface MyPageDataSource {
    interface MyPageCallback{
        fun onSuccessFetchUserData(myPageResponse: MyPageResponse)
        fun onFailedFetchUserData(networkError: Boolean, errorBody: ResponseBody?)
    }

    fun requestFetchUserData(userIdx: Int, callback: MyPageCallback)
}