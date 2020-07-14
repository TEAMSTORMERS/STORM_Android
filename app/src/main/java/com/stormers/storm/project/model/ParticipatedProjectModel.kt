package com.stormers.storm.project.model

import com.google.gson.annotations.SerializedName

data class ParticipatedProjectModel(

    val projectIdx: Int,

    val projectName: String,

    val projectCard: List<String>
)