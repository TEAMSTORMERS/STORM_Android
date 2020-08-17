package com.stormers.storm.project.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project_entity")
data class ProjectEntity (
    @PrimaryKey
    @ColumnInfo(name = "project_idx")
    val projectIdx: Int,

    @ColumnInfo(name = "project_name")
    val projectName: String?,

    @ColumnInfo(name = "project_comment")
    val projectComment: String?,

    @ColumnInfo(name = "project_participants_idx")
    val projectParticipantsIdx: Int?
)
