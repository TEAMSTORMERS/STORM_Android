package com.stormers.storm.SignUp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface InterfaceSignUp{
    @POST("/user")
    fun interfaceSignUp(@Body body : SignUpModel) : Call<ResponseSignUpModel>
}