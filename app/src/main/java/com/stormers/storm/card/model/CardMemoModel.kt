package com.stormers.storm.card.model

import com.google.gson.annotations.SerializedName

data class CardMemoModel(
    @SerializedName("user_idx")
    val userIdx: Int,

    @SerializedName("card_idx")
    val cardIdx: Int,

    @SerializedName("memo_content")
    val memo: String
) {
    override fun toString(): String {
        return "userIdx: $userIdx, cardIdx: $cardIdx, memo: $memo"
    }
}