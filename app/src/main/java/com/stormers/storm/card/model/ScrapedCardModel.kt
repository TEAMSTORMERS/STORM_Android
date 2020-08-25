package com.stormers.storm.card.model

data class ScrapedCardModel(
    val projectName: String,
    val scrapCount: Int,
    val cardItem: List<ScrapCardWithRoundInfo>
)