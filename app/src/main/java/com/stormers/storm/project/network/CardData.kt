package com.stormers.storm.project.network

data class CardData (
    val card_idx : Int,
    val card_img : String?,
    val card_txt : String?,
    val memo_content : String,
    val user_img : String,
    val scrap_flag : Int
)