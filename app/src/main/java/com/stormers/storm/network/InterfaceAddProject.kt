package com.stormers.storm.network

import com.stormers.storm.project.model.AddProjectModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface InterfaceAddProject {
    @POST("/project")
    fun addProject(@Body body:AddProjectModel) :Call<ResponseAddProject>

}