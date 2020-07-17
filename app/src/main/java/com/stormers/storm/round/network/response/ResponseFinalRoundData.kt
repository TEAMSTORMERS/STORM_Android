package com.stormers.storm.round.network.response

import com.stormers.storm.round.model.RoundDescriptionModel

data class ResponseFinalRoundData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<RoundDescriptionModel>
)