package com.stormers.storm.round.network.response

import com.stormers.storm.round.model.FinalRoundData

data class ResponseRoundUserImageModel(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<FinalRoundData>
)