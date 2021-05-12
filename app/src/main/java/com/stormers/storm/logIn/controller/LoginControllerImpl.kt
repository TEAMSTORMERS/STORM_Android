package com.stormers.storm.logIn.controller

import com.stormers.storm.logIn.service.LoginService
import com.stormers.storm.logIn.model.LoginRequest
import com.stormers.storm.logIn.model.LoginResponse
import com.stormers.storm.network.RequestCallback
import com.stormers.storm.network.request
import okhttp3.ResponseBody

class LoginControllerImpl(private val loginService: LoginService) : LoginController {
    override fun requestLogin(
        email: String,
        password: String,
        callback: LoginController.LoginCallback
    ) {
        loginService.requestLogIn(LoginRequest(email, password))
            .request(object : RequestCallback<LoginResponse> {

                override fun onSuccess(response: LoginResponse) {
                    callback.onLoginSuccess(response.userId!!)
                }

                override fun onFailure(isNetworkError: Boolean, errorBody: ResponseBody?) {
                    callback.onLoginFailed(isNetworkError)
                }
            })
    }
}