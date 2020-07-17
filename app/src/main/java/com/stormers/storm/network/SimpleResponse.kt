package com.stormers.storm.network

data class SimpleResponse (
    val status : Int,
    val success : Boolean,
    val message : String
)