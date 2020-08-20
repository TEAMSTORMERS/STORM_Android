package com.stormers.storm.project.model

import com.google.gson.annotations.SerializedName

data class ProjectLookupModel(
    @SerializedName("project_idx")
    val projectIdx: Int,

    @SerializedName("project_name")
    val projectName: String,

    @SerializedName("project_comment")
    val projectComment: String
)
