package com.stormers.storm.card

import com.stormers.storm.card.model.CardEntity

enum class CardType {
    DRAWING, TEXT;

    companion object {
        fun scrapConverter(isScraped: Int): Boolean {
            return isScraped == CardEntity.TRUE
        }

        fun scrapConverter(isScraped: Boolean): Int {
            return if (isScraped) {
                CardEntity.TRUE
            } else {
                CardEntity.FALSE
            }
        }

        fun typeConverter(cardType: CardType): Int {
            return if (cardType == DRAWING) {
                CardEntity.DRAWING
            } else {
                CardEntity.TEXT
            }
        }

        fun typeConverter(cardType: Int): CardType {
            return if (cardType == CardEntity.DRAWING) {
                DRAWING
            } else {
                TEXT
            }
        }
    }
}