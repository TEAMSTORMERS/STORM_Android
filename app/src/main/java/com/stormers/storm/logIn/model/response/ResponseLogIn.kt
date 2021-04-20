package com.stormers.storm.logIn.model.response

data class ResponseLogIn(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : Int
)