package com.stormers.storm.project.network.response

import com.stormers.storm.project.model.ProjectInfoForUserImageModel

data class ResponseProjectFinalInfoModel(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : ProjectInfoForUserImageModel
)