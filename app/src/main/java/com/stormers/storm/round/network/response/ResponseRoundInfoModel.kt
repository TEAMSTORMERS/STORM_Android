package com.stormers.storm.round.network.response

import com.stormers.storm.round.model.RoundInfoModel

data class ResponseRoundInfoModel(
    val status : String,
    val success : Boolean,
    val message : String,
    val data : RoundInfoModel
)