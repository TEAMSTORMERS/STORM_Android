package com.stormers.storm.round.model

data class ResponseRoundCountModel(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : Int
)