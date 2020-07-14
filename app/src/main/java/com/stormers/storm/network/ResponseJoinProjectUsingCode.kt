package com.stormers.storm.network

import com.stormers.storm.project.model.ProjectDataModel
import com.stormers.storm.project.model.ProjectIndexModel

data class ResponseJoinProjectUsingCode(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : ProjectIndexModel
)