package com.stormers.storm.card.model

data class CardEnumModel (
    val cardIdx: Int,
    val projectIdx: Int,
    val roundIdx: Int,
    var isScraped: Boolean,
    val cardType: CardType,
    val cardContent: Strin
)