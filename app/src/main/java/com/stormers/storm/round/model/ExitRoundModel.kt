package com.stormers.storm.round.model

import com.google.gson.annotations.SerializedName

data class ExitRoundModel(
    @SerializedName("user_idx")
    val userIdx : Int,
    @SerializedName("round_idx")
    val roundIdx : Int
)