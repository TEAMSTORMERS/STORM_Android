package com.stormers.storm.round.model

data class ResponseRoundInfoModel(
    val status : String,
    val success : Boolean,
    val message : String,
    val data : RoundInfoModel
)