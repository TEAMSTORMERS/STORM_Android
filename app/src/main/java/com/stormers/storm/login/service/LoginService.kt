package com.stormers.storm.login.service

import com.stormers.storm.login.model.LoginRequest
import com.stormers.storm.login.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService{
    @POST("/user/signin")
    fun requestLogIn(@Body body : LoginRequest) : Call<LoginResponse>
}