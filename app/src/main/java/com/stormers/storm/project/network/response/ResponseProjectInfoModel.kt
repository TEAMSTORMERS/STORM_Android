package com.stormers.storm.project.network.response

import com.stormers.storm.project.model.ProjectInfoModel

data class ResponseProjectInfoModel(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : ProjectInfoModel
)
