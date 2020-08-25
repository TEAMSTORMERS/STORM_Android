package com.stormers.storm.round.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.kakao.usermgmt.response.model.User

@Entity(
    tableName = "round_participant_entity",
    primaryKeys = ["round_idx", "user_idx"],
    foreignKeys = [
        ForeignKey(
            entity = Round::class,
            parentColumns = ["round_idx"],
            childColumns = ["round_idx"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_idx"],
            childColumns = ["user_idx"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class RoundParticipant {
    @ColumnInfo(name = "round_idx")
    val roundIdx: Int = 0

    @ColumnInfo(name = "user_idx")
    val userIdx: Int = 0
}