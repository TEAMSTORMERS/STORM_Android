package com.stormers.storm.network

data class ResponseData<T> (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : T
)