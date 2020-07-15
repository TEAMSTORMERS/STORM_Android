package com.stormers.storm.project.model

data class ResponseProjectInfoModel(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : ProjectInfoModel
)
