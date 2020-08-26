package com.stormers.storm.card.model

import com.google.gson.annotations.SerializedName

data class ScrapedCardWithRoundInfo (
    @SerializedName("round_number")
    val roundNumber: Int,

    @SerializedName("round_purpose")
    val roundPurpose: String,

    @SerializedName("round_time")
    val roundTime: Int,

    @SerializedName("card_idx")
    val cardIdx: Int,

    @SerializedName("card_img")
    val cardImage: String?,

    @SerializedName("card_txt")
    val cardText: String?,

    @SerializedName("memo_content")
    var cardMemo: String?,

    @SerializedName("user_img")
    val userImage: String) {

    var isScraped = true
}