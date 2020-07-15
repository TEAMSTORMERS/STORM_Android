package com.stormers.storm.network

import com.stormers.storm.project.model.ResponseProjectInfoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceProjectInfo {
    @GET
    fun responseProjectinfo(@Path("/project/:project_idx") ProjectIdx : String) :Call<ResponseProjectInfoModel>
}