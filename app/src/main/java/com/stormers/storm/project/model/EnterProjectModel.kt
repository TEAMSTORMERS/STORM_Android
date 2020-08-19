package com.stormers.storm.project.model

import com.google.gson.annotations.SerializedName

data class EnterProjectModel(
    @SerializedName("user_idx")
    val userIdx : Int,
    @SerializedName("project_idx")
    val projectIdx : Int
)