package com.stormers.storm.round.model

import com.google.gson.annotations.SerializedName

data class RoundEnterModel (
    @SerializedName("user_idx")
    val userIdx : Int,
    @SerializedName("project_idx")
    val projectIdx : Int
)