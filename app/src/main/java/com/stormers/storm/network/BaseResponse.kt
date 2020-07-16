package com.stormers.storm.network

data class BaseResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : Int
)