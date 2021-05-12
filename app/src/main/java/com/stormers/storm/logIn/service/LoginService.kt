package com.stormers.storm.logIn.service

import com.stormers.storm.logIn.model.LoginRequest
import com.stormers.storm.logIn.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService{
    @POST("/user/signin")
    fun requestLogIn(@Body body : LoginRequest) : Call<LoginResponse>
}