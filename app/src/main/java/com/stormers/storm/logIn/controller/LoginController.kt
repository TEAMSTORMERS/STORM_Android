package com.stormers.storm.logIn.controller

interface LoginController {

    interface LoginCallback {
        fun onLoginSuccess(userId: Int)

        fun onLoginFailed(networkError: Boolean)
    }

    fun requestLogin(email: String, password: String, callback: LoginCallback)
}