package com.stormers.storm.card.model

import androidx.room.ColumnInfo

data class ScrapCardWithRoundInfo (
    @ColumnInfo(name = "round_number")
    val roundNumber: Int,

    @ColumnInfo(name = "round_purpose")
    val roundPurpose: String,

    @ColumnInfo(name = "round_time")
    val roundTime: Int,

    @ColumnInfo(name = "card_idx")
    val cardIdx: Int,

    @ColumnInfo(name = "card_img")
    val cardImage: String,

    @ColumnInfo(name = "card_txt")
    val cardText: String
)