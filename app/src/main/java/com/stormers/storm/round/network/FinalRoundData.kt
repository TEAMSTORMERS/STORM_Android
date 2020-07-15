package com.stormers.storm.round.network


data class FinalRoundData (
    val round_number : Int,
    val round_purpose : String,
    val round_time : Int,
    val round_participant : List<RoundData>
)