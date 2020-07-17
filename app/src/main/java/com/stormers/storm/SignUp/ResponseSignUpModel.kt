package com.stormers.storm.SignUp

data class ResponseSignUpModel(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : Int
)