package com.stormers.storm.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stormers.storm.mypage.datasource.MyPageDataSource
import com.stormers.storm.mypage.model.MyPageModel
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
    private val _userData = MutableLiveData<MyPageModel>()
    val userData: LiveData<MyPageModel>
        get() = _userData

    val userName = MutableLiveData<String>()

    private val _isSuccessFetchData = MutableLiveData<Map<Boolean, String>>()
    val isSuccessFetchData: LiveData<Map<Boolean, String>>
        get() = _isSuccessFetchData

    private val userIdx = GlobalApplication.userIdx

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        dataSource.requestFetchUserData(19, object : MyPageDataSource.MyPageCallback {
            override fun onSuccessFetchUserData(responseMyPage: ResponseMyPage) {
                _userData.value = responseMyPage.data
                userName.value = responseMyPage.data.userName
                _isSuccessFetchData.value = mapOf(true to responseMyPage.message)
            }

            override fun onFailedFetchUserData(networkError: Boolean, errorBody: ResponseBody?) {
                val error = errorBody ?: return
                val jsonObject = JSONObject(error.string())
                _isSuccessFetchData.value = mapOf(false to jsonObject.toString())
            }
        })
    }
}