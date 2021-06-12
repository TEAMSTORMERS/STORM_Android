package com.stormers.storm.mypage.datasource

import com.stormers.storm.mypage.MyPageService
import com.stormers.storm.mypage.model.ResponseMyPage
import com.stormers.storm.network.RequestCallback
import com.stormers.storm.network.request
import okhttp3.ResponseBody
import javax.inject.Inject

class MyPageDataSourceImpl @Inject constructor(
    private val myPageService: MyPageService
): MyPageDataSource {
    override fun requestFetchUserData(
        userIdx: Int,
        callback: MyPageDataSource.MyPageCallback
    ) = myPageService.getMypageData(userIdx)
        .request(object : RequestCallback<ResponseMyPage>{
            override fun onSuccess(responseMyPage: ResponseMyPage) {
                callback.onSuccessFetchUserData(responseMyPage)
            }

            override fun onFailure(isNetworkError: Boolean, errorBody: ResponseBody?) {
                callback.onFailedFetchUserData(isNetworkError, errorBody)
            }
        })
}