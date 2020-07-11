package com.stormers.storm.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scraped_card_entity")
data class SavedCardEntity (
    @ColumnInfo(name = "project_idx")
    var projectIdx: Int,

    @ColumnInfo(name = "round_idx")
    var roundIdx: Int,

    @ColumnInfo(name = "file_name")
    var fileName: String
) {
    @PrimaryKey(autoGenerate = true)
    var cardId: Int = 0
}