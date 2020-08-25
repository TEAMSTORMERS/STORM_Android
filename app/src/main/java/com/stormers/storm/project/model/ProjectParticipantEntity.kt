package com.stormers.storm.project.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.stormers.storm.project.data.Project
import com.stormers.storm.user.User

@Entity(
    tableName = "project_participant_entity",
    primaryKeys = ["project_idx", "user_idx"],
    foreignKeys = [
        ForeignKey(
            entity = Project::class,
            parentColumns = ["project_idx"],
            childColumns = ["project_idx"],
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
class ProjectParticipantEntity {
    @ColumnInfo(name = "project_idx")
    val projectIdx: Int = 0

    @ColumnInfo(name = "user_idx")
    val userIdx: Int = 0
}