package com.stormers.storm.logIn.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("user_email") val email : String,
    @SerializedName("user_password") val password : String
)