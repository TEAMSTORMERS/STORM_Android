package com.stormers.storm.card.model

import com.google.gson.annotations.SerializedName

data class CardWithOwnerModel (
    @SerializedName("card_idx")
    val cardIdx : Int,

    @SerializedName("card_img")
    val cardImage : String?,

    @SerializedName("card_txt")
    val cardText : String?,

    @SerializedName("memo_content")
    var cardMemo : String?,

    @SerializedName("user_idx")
    val userIdx : Int,

    @SerializedName("user_img")
    val userImg : String,

    @SerializedName("scrap_flag")
    var isScraped : Int
)