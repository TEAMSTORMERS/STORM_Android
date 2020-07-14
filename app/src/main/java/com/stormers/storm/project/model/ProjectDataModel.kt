package com.stormers.storm.project.model

import com.google.gson.annotations.SerializedName

data class ProjectDataModel (
    @SerializedName("project_code")
    val projectCode : String,
    @SerializedName("project_idx")
    val projectIdx : Int

)