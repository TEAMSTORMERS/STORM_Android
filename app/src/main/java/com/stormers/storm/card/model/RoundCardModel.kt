package com.stormers.storm.card.model

data class RoundCardModel(
    val project_name: String,
    val round_number: Int,
    val round_purpose: String,
    val round_time: Int,
    val card_list: List<ResponseCardModel>
)