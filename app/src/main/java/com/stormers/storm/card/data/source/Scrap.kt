package com.stormers.storm.card.data.source

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "scrap")
data class Scrap(
    @ColumnInfo(name = "scrap_idx")
    val scrapIdx: Int,

    @ColumnInfo(name = "card_idx")
    val cardIdx: Int,

    @ColumnInfo(name = "user_idx")
    val userIdx: Int
)