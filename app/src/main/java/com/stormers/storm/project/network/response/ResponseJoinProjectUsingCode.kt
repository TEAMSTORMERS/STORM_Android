package com.stormers.storm.project.network.response

import com.stormers.storm.project.model.ProjectDataModel
import com.stormers.storm.project.model.ProjectIndexModel

data class ResponseJoinProjectUsingCode(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : ProjectIndexModel
)