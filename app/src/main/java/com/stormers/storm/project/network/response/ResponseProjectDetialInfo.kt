package com.stormers.storm.project.network.response

import com.stormers.storm.project.model.ProjectDetailInfo

data class ResponseProjectDetialInfo(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : ProjectDetailInfo
)