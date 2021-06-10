package com.stormers.storm.login.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    @SerializedName("data") val userId : Int?
)