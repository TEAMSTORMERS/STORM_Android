package com.stormers.storm.card

import com.stormers.storm.card.data.Card

enum class CardType {
    DRAWING, TEXT;

    companion object {
        fun scrapConverter(isScraped: Int): Boolean {
            return isScraped == Card.TRUE
        }

        fun scrapConverter(isScraped: Boolean): Int {
            return if (isScraped) {
                Card.TRUE
            } else {
                Card.FALSE
            }
        }

        fun typeConverter(cardType: CardType): Int {
            return if (cardType == DRAWING) {
                Card.DRAWING
            } else {
                Card.TEXT
            }
        }

        fun typeConverter(cardType: Int): CardType {
            return if (cardType == Card.DRAWING) {
                DRAWING
            } else {
                TEXT
            }
        }
    }
}