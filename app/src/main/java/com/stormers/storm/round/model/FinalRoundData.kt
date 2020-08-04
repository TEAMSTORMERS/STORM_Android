package com.stormers.storm.round.model

import com.google.gson.annotations.SerializedName


data class FinalRoundData (
    @SerializedName("roundIdx")
    val round_idx : Int,
    @SerializedName("roundNumber")
    val round_number : Int,
    @SerializedName("roundPurpose")
    val round_purpose : String,
    @SerializedName("roundTime")
    val round_time : Int,
    @SerializedName("roundParticipant")
    val round_participant : List<RoundData>
)