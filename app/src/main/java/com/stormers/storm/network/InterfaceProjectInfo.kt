package com.stormers.storm.network

import com.stormers.storm.project.model.ResponseProjectInfoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceProjectInfo {
    @GET("/project/{project_idx}")
    fun responseProjectinfo(@Path("project_idx") ProjectIdx : Int) :Call<ResponseProjectInfoModel>
}