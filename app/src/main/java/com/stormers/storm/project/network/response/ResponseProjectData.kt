package com.stormers.storm.project.network.response

import com.stormers.storm.project.model.ProjectData

data class ResponseProjectData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : ProjectData
)

