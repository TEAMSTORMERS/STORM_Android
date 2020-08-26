package com.stormers.storm.card.model

import com.google.gson.annotations.SerializedName

data class ScrapedCardRelationModel(
    @SerializedName("user_idx")
    val userIdx: Int,

    @SerializedName("card_idx")
    val cardIdx: Int
)