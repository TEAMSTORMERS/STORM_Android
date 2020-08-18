package com.stormers.storm.card.model

data class ResponseCardModel (
    val card_idx : Int,
    val card_img : String?,
    val card_txt : String?,
    val memo_content : String,
    val user_idx : Int,
    val user_img : String,
    val scrap_flag : Int
)