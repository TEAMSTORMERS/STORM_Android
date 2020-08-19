package com.stormers.storm.project.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project_participant_entity")
class ProjectParticipantEntity (
    @ColumnInfo(name = "project_idx")
    val projectIdx: Int,

    @ColumnInfo(name = "user_idx")
    val userIdx: Int
) {
   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "project_participant_idx")
   var projectParticipantIdx: Int = 0
}