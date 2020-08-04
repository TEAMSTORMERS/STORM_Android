package com.stormers.storm.round.model

import com.google.gson.annotations.SerializedName

data class RoundData (
    @SerializedName("userName")
    val user_name : String,
    @SerializedName("userImage")
    val user_img : String
)