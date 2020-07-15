package com.stormers.storm.user

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("user_img")
    val profileUrl: String,
    @SerializedName("user_name")
    val name: String
)
