package com.stormers.storm.card.network

import com.stormers.storm.card.model.RoundCardModel

data class ResponseCardData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : RoundCardModel
)