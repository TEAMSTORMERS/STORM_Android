package com.stormers.storm.project.model

import com.google.gson.annotations.SerializedName

data class ProjectInfoForUserImageModel(
    @SerializedName("project_name")
    val projectName : String,
    @SerializedName("project_date")
    val projectDate : String,
    @SerializedName("round_count")
    val roundCount : Int,
    @SerializedName("project_participants_list")
    val projectParticipantsList : List<String>
)