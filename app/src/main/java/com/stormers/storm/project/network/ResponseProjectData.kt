package com.stormers.storm.project.network

data class ResponseProjectData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : ProjectData
)

