package com.stormers.storm.UserWithdrawal

import com.stormers.storm.network.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestPasswordCheck{

    @GET("/user/checkPassword/{user_idx}/{user_password}")
    fun requestPasswordCheck(@Path ("user_idx") userIdx :Int, @Path("user_password") userPassword : String) : Call<Response>
}