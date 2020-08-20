package com.stormers.storm.round.model

import com.google.gson.annotations.SerializedName

data class RoundInfoModel(
    @SerializedName("round_number")
    val roundNumber : Int,
    @SerializedName("round_purpose")
    val roundPurpose : String,
    @SerializedName("round_time")
    val roundTime : Int
)