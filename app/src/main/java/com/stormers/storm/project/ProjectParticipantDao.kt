package com.stormers.storm.project

import androidx.room.Dao
import androidx.room.Query
import com.stormers.storm.base.BaseDao
import com.stormers.storm.project.model.ProjectParticipantEntity

@Dao
abstract class ProjectParticipantDao: BaseDao<ProjectParticipantEntity> {

    @Query("SELECT user_idx FROM project_participant_entity WHERE project_idx = :projectIdx")
    abstract fun getAll(projectIdx: Int): List<Int>?

    @Query("SELECT * FROM project_participant_entity WHERE project_idx = :projectIdx AND user_idx = :userIdx")
    abstract fun get(projectIdx: Int, userIdx: Int): ProjectParticipantEntity?
}