package com.stormers.storm.project.network

import com.stormers.storm.project.model.ResponseParticipatedProject
import retrofit2.Call
import retrofit2.http.*

interface ProjectInterface {
    @GET("project/finalInfo/{project_idx}")
    fun responseProjectData(@Path("project_idx") projectIdx : Int) : Call<ResponseProjectData>

    @GET("/project/user/{user_idx}")
    fun requestParticipatedProject(@Path("user_idx") userIdx: Int) : Call<ResponseParticipatedProject>
}