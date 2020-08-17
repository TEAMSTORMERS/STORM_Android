package com.stormers.storm.round.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "round_entity")
data class RoundModel(
    @PrimaryKey
    @ColumnInfo(name = "round_idx")
    val roundIdx: Int,

    @ColumnInfo(name = "round_number")
    val roundNumber: Int?,

    @ColumnInfo(name = "round_purpose")
    val roundPurpose: String?,

    @ColumnInfo(name = "round_time")
    val roundTime: Int?,

    @ColumnInfo(name = "project_idx")
    val projectIdx: Int,

    @ColumnInfo(name = "round_participants_idx")
    var roundParticipantsIdx: Int?
) {
    override fun toString(): String {
        return "roundIdx : $roundIdx, roundNumber : $roundNumber, roundPurpose : $roundPurpose," +
                " roundTime : $roundTime, projectIdx: $projectIdx, numberOfParticipant : ${roundParticipantsIdx}}"
    }
}
