package com.stormers.storm.card.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scraped_card_entity")
data class CardEntity (
    @PrimaryKey
    @ColumnInfo(name = "card_idx")
    val cardIdx: Int,

    @ColumnInfo(name = "project_idx")
    val projectIdx: Int,

    @ColumnInfo(name = "round_idx")
    val roundIdx: Int,

    @ColumnInfo(name = "user_idx")
    val userIdx: Int,

    @ColumnInfo(name = "scraped")
    var isScraped: Int,

    @ColumnInfo(name = "type")
    val cardType: Int,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "memo")
    var memo: String?
) {
    companion object {
        const val TRUE = 1
        const val FALSE = 0

        const val DRAWING = 0
        const val TEXT = 1
    }

    override fun toString(): String {
        return "cardIdx: $cardIdx, projectIdx: $projectIdx, roundIdx: $roundIdx, isScraped: $isScraped, " +
                "cardType: $cardType, content: ${content}, memo: $memo"
    }
}