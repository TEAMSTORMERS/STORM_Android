package com.stormers.storm.network

import com.stormers.storm.project.model.JoinProjectUsingCodeModel
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.POST

interface InterfaceJoinProjectUsingCode{
    @POST("/project/enter")
    fun joinProjectUsingCode(@Body body:JoinProjectUsingCodeModel):Callback<JoinProjectUsingCodeModel>


}