package com.stormers.storm.project

import androidx.room.Dao
import androidx.room.Query
import com.stormers.storm.base.BaseDao
import com.stormers.storm.project.model.ProjectEntity

@Dao
abstract class ProjectDao: BaseDao<ProjectEntity> {

    @Query("SELECT * FROM project_entity")
    abstract fun getAll(): List<ProjectEntity>?

    @Query("SELECT * FROM project_entity WHERE project_idx = :projectIdx")
    abstract fun get(projectIdx: Int): ProjectEntity?
}