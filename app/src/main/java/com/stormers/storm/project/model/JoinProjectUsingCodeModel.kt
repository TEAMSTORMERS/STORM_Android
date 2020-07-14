package com.stormers.storm.project.model

import com.google.gson.annotations.SerializedName

data class JoinProjectUsingCodeModel(
    @SerializedName("user_idx")
    val userIdx : Int,
    @SerializedName("project_code")
    val projectCode : String
)