package com.stormers.storm.roundparticipant

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "round_participant_entity")
data class RoundParticipantEntity(

    @ColumnInfo(name = "round_idx")
    val roundIdx: Int,

    @ColumnInfo(name = "user_idx")
    val userIdx: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "round_participant_idx")
    var roundParticipantIdx: Int = 0
}