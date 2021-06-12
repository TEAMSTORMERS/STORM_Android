package com.stormers.storm.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stormers.storm.mypage.datasource.MyPageDataSource
import com.stormers.storm.mypage.model.ResponseMyPage
import com.stormers.storm.ui.GlobalApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.ResponseBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val dataSource: MyPageDataSource
) : ViewModel() {
    private val _userImage = MutableLiveData<String>()
    val userImage: LiveData<String>
        get() = _userImage

    val userName = MutableLiveData<String>()

    private val userIdx = GlobalApplication.userIdx

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        dataSource.requestFetchUserData(userIdx, object : MyPageDataSource.MyPageCallback {
            override fun onSuccessFetchUserData(responseMyPage: ResponseMyPage) {
                _userImage.value = responseMyPage.data.userImage
                userName.value = responseMyPage.data.userName
            }
            override fun onFailedFetchUserData(networkError: Boolean, errorBody: ResponseBody?) {
                val errorBody = errorBody ?: return
                val jsonObject = JSONObject(errorBody.string())
                Log.e("error", "networkError: $networkError, errorMessage: $jsonObject")
            }
        })
    }
}