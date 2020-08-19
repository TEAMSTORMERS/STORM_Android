package com.stormers.storm.UserWithdrawal.network

import com.stormers.storm.UserWithdrawal.model.PasswordCheckModel
import com.stormers.storm.network.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestPasswordCheck{

    @POST("/user/checkPassword")
    fun requestPasswordCheck(@Body body : PasswordCheckModel) : Call<Response>
}