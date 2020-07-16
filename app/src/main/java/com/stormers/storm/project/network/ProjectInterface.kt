package com.stormers.storm.project.network

import com.stormers.storm.project.network.ResponseProjectData
import retrofit2.Call
import retrofit2.http.*

interface ProjectInterface {
    @GET("project/finalInfo/{project_idx}")
    fun responseProjectData(@Path("project_idx") projectIdx : String) : Call<ResponseProjectData>
}