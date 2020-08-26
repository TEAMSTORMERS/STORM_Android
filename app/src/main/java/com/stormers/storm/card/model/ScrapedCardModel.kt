package com.stormers.storm.card.model

import com.google.gson.annotations.SerializedName

data class ScrapedCardModel(
    @SerializedName("project_name")
    val projectName: String,

    @SerializedName("scrap_count")
    val scrapCount: Int,

    @SerializedName("card_item")
    val cardItem: List<ScrapedCardWithRoundInfo>
)