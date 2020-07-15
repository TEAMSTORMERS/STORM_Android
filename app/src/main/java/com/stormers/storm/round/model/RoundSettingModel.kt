package com.stormers.storm.round.model

import com.google.gson.annotations.SerializedName

data class RoundSettingModel (
    @SerializedName("project_idx")
    val projectIdx : Int,
    @SerializedName("round_purpose")
    val roundPurpose : String,
    @SerializedName("round_time")
    val roundTime : Int
)