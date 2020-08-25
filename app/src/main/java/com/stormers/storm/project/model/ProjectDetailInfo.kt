package com.stormers.storm.project.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class ProjectDetailInfo(
    @ColumnInfo(name = "project_idx")
    val projectIdx : Int,

    @SerializedName("project_name")
    @ColumnInfo(name = "project_name")
    val projectName : String,

    @SerializedName("project_date")
    @ColumnInfo(name = "project_date")
    val projectDate : String,

    @SerializedName("round_count")
    @ColumnInfo(name = "round_count")
    var roundCount : Int,

    @SerializedName("project_participants_list")
    var projectParticipantsList : List<String>
)