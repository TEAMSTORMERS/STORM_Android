package com.stormers.storm.project.network.response

data class ResponseJoinProjectUsingCode(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : Int
)