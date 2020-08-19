package com.stormers.storm.UserWithdrawal.network

import com.stormers.storm.UserWithdrawal.model.UserWithdrawalModel
import com.stormers.storm.network.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestWithdrawal{
    @POST("/user/withdrawal")
    fun requestWithdrawal(@Body body : UserWithdrawalModel) : Call<Response>
}