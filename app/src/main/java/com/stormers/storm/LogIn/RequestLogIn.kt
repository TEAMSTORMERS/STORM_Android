package com.stormers.storm.LogIn

import com.stormers.storm.LogIn.model.LogInModel
import com.stormers.storm.LogIn.model.response.ResponseLogIn
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestLogIn{
    @POST("/user/signin")
    fun requestLogIn(@Body body : LogInModel) : Call<ResponseLogIn>
}