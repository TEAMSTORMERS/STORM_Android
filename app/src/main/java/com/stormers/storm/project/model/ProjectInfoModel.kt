package com.stormers.storm.project.model

import com.google.gson.annotations.SerializedName

data class ProjectInfoModel(
    @SerializedName("project_name")
    val projectName : String,
    @SerializedName("project_comment")
    val projectComment : String,
    @SerializedName("project_code")
    val projectCode : String
)
