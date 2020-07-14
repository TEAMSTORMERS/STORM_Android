package com.stormers.storm.project.model

import com.google.gson.annotations.SerializedName

data class ProjectIndexModel(
    @SerializedName("project_idx")
    val projectIdx : Int
)