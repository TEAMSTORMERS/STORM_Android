package com.stormers.storm.card.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scraped_card_entity")
data class SavedCardEntity (
    @ColumnInfo(name = "project_idx")
    var projectIdx: Int,

    @ColumnInfo(name = "round_idx")
    var roundIdx: Int,

    @ColumnInfo(name = "scraped")
    var isScraped: Int,

    @ColumnInfo(name = "bitmap")
    var bitmap: String,

    @ColumnInfo(name = "memo")
    var memo: String
) {
    @PrimaryKey(autoGenerate = true)
    var cardId: Int = 0

    companion object {
        const val TRUE = 1
        const val FALSE = 0
    }
}