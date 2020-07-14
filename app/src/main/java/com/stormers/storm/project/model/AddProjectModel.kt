package com.stormers.storm.project.model

import com.google.gson.annotations.SerializedName

data class AddProjectModel(
    @SerializedName("project_name")
    val projectName :String,
    @SerializedName("project_comment")
    val projectComment: String,
    @SerializedName("user_idx")
    val userIdx: Int
)