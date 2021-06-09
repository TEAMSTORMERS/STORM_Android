package com.stormers.storm.logIn

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stormers.storm.logIn.controller.LoginController
import com.stormers.storm.preference.UserPreferenceManager
import com.stormers.storm.util.TextUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginController: LoginController,
    private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _isLoginFail = MutableLiveData(false)
    val isLoginFail: LiveData<Boolean>
        get() = _isLoginFail

    private val _isLoginSuccessful = MutableLiveData(false)
    val isLoginSuccessful: LiveData<Boolean>
        get() = _isLoginSuccessful

    val isAutoLogin = MutableLiveData(userPreferenceManager.getAutoLogin())

    fun requestLogin() {
        val email = email.value
        val password = password.value
        if (email.isNullOrBlank() || password.isNullOrBlank() || !TextUtils.isEmailPattern(email)) {
            _isLoginFail.value = true
            return
        }

        loginController.requestLogin(email, password, object : LoginController.LoginCallback {
            override fun onLoginSuccess(userId: Int) {
                Log.d("LoginViewModel", "requestLogin(): Login success. userId : $userId")
                setAutoLogin()
                userPreferenceManager.setUserId(userId)
                _isLoginSuccessful.value = true
            }

            override fun onLoginFailed(networkError: Boolean) {
                _isLoginFail.value = true
            }
        })
    }

    private fun setAutoLogin() {
        userPreferenceManager.setAutoLogin(isAutoLogin.value!!)
    }
}