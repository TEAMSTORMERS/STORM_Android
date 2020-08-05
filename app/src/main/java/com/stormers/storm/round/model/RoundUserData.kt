package com.stormers.storm.round.model

import com.google.gson.annotations.SerializedName

data class RoundUserData (
    @SerializedName("user_name")
    val userName : String,
    @SerializedName("user_img")
    val userImg : String
)