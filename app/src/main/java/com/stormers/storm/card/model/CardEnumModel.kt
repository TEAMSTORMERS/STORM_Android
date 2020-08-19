package com.stormers.storm.card.model

import com.stormers.storm.card.CardType

data class CardEnumModel (
    val cardIdx: Int,
    val projectIdx: Int,
    val roundIdx: Int,
    var isScraped: Boolean,
    val cardType: CardType,
    val cardContent: String
)