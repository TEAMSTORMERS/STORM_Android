package com.stormers.storm.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stormers.storm.mypage.entity.MypageData
import com.stormers.storm.ui.GlobalApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val repository: MyPageRepository
): ViewModel() {
    private val _myPageData = MutableLiveData<MypageData>()
    val myPageData: LiveData<MypageData>
        get() = _myPageData

    private val userIdx = GlobalApplication.userIdx


    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            _myPageData.postValue(repository.getMyPageData(userIdx))
        }
    }
}