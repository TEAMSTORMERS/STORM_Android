package com.stormers.storm.card.model

import com.stormers.storm.card.CardType
import com.stormers.storm.user.UserModel

data class CardModel(
    val cardIdx: Int,
    var isScraped: Boolean,
    val cardType: CardType,
    val cardContent: String,
    var cardMemo: String?,
    val cardOwner: UserModel
) {
    override fun toString(): String {
        return "cardIdx: $cardIdx, isScraped: $isScraped, cardType: $cardType, " +
                "cardContent: ${cardContent}, cardMemo: $cardMemo, cardOwener: $cardOwner"
    }
}
