package com.stormers.storm.project.network

data class ResponseCardData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : CardData
)