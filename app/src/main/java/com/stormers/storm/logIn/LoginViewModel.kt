package com.stormers.storm.logIn

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stormers.storm.customview.StormEditText
import com.stormers.storm.util.TextUtils

class LoginViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _isLoginFail = MutableLiveData(false)
    val isLoginFail : LiveData<Boolean>
        get() = _isLoginFail

    fun requestLogin() {
        val email = email.value
        val password = password.value
        if (email.isNullOrBlank() || password.isNullOrBlank() || !TextUtils.isEmailPattern(email)) {
            _isLoginFail.value = true
            return
        }

        //Todo: Login 모듈을 주입받아 로그인 요청 작업을 처리하여야 함
        Log.d("LoginViewModel", "requestLogin() : Login attempt ($email, $password)")
    }
}