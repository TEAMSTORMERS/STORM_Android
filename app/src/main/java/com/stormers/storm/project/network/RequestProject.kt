package com.stormers.storm.project.network

import com.stormers.storm.project.model.*
import com.stormers.storm.project.network.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RequestProject {
    @POST("/project")
    fun addProject(@Body body:AddProjectModel) :Call<ResponseAddProject>

    @POST("/project/enter")
    fun joinProjectUsingCode(@Body body: JoinProjectUsingCodeModel) : Call<ResponseJoinProjectUsingCode>

    @GET("/project/{project_idx}")
    fun responseProjectinfo(@Path("project_idx") ProjectIdx : Int) :Call<ResponseProjectInfoModel>

    @GET("/project/enter/{project_idx}")
    fun getProjectUserList(@Path("project_idx") projectIdx : Int ):Call<ResponseProjectUserListModel>

    @GET("project/finalInfo/{project_idx}")
    fun responseProjectData(@Path("project_idx") projectIdx : Int) : Call<ResponseProjectData>

    @GET("/project/user/{user_idx}")
    fun requestParticipatedProject(@Path("user_idx") userIdx: Int) : Call<ResponseParticipatedProject>
}