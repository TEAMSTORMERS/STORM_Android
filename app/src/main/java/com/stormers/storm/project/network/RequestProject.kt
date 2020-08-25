package com.stormers.storm.project.network

import com.stormers.storm.network.SimpleResponse
import com.stormers.storm.project.model.*
import com.stormers.storm.project.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface RequestProject {
    @POST("/project")
    fun addProject(@Body body:AddProjectModel) :Call<ResponseAddProject>

    @POST("/project/enter")
    fun enterProject(@Body body: EnterProjectModel) : Call<ResponseJoinProjectUsingCode>

    @GET("/project/{project_idx}")
    fun responseProjectinfo(@Path("project_idx") ProjectIdx : Int) :Call<ResponseProjectInfoModel>

    @GET("/project/enter/{project_idx}")
    fun getProjectUserList(@Path("project_idx") projectIdx : Int ):Call<ResponseParticipant>

    @GET("project/finalInfo/{project_idx}")
    fun getProjectDetailInfo(@Path("project_idx") projectIdx : Int) : Call<ResponseProjectDetialInfo>

    @GET("/project/user/{user_idx}")
    fun getProjectPreviews(@Path("user_idx") userIdx: Int) : Call<ResponseProjectPreviews>

    @GET("/project/info/{project_code}")
    fun lookupProject(@Path("project_code") projectCode: String) : Call<ResponseLookupProject>

    @PUT("/project/status/{project_idx}")
    fun projectStart(@Path("project_idx") projectIdx: Int) : Call<SimpleResponse>

    @PUT("/project/finish/{project_idx}")
    fun finishProject(@Path("project_idx") projectIdx: Int) : Call<SimpleResponse>
}