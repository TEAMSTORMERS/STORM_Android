package com.stormers.storm.network

import com.stormers.storm.project.model.ResponseProjectUserListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceProjectUser {
    @GET("/project/enter/{project_idx}")
    fun getProjectUserList(@Path("project_idx") projectIdx : Int ):Call<ResponseProjectUserListModel>
}