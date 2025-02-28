package com.stormers.storm.project.data.source.local

//import androidx.room.*
//import com.stormers.storm.base.BaseDao
//import com.stormers.storm.project.data.Project
//import com.stormers.storm.project.model.ProjectDetailInfo
//import com.stormers.storm.project.data.ProjectParticipant
//import com.stormers.storm.project.model.ProjectNameWithScrapedCardCount
//import com.stormers.storm.project.model.ProjectPreviewModel
//
//@Dao
//abstract class ProjectDao: BaseDao<Project> {

//    @Query("SELECT * FROM project_entity")
//    abstract fun getAll(): List<Project>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    abstract fun takePartInProject(projectParticipant: ProjectParticipant)
//
//    @Query("SELECT p.project_idx, p.project_name FROM project_participant_entity pp JOIN project_entity p ON pp.project_idx = p.project_idx WHERE pp.user_idx = :userIdx")
//    abstract fun getProjectPreviews(userIdx: Int): List<ProjectPreviewModel>
//
//    @Query("SELECT project_name, project_date FROM project_entity WHERE project_idx = :projectIdx")
//    abstract fun getProject(projectIdx: Int): ProjectDetailInfo?
//
//    @Query("SELECT COUNT(*) FROM round_entity WHERE project_idx = :projectIdx")
//    abstract fun getRoundCount(projectIdx: Int): Int
//
//    @Query("SELECT * FROM project_entity WHERE project_idx = :projectIdx")
//    abstract fun get(projectIdx: Int): Project?
//
//    @Query("SELECT project_name, COUNT(scrap_idx) AS scrap_count FROM project_entity JOIN card_entity ON project_entity.project_idx = card_entity.project_idx JOIN scrap ON scrap.card_idx = card_entity.card_idx WHERE project_entity.project_idx = :projectIdx AND scrap.user_idx = :userIdx")
//    abstract fun getProjectNameOfScrapedCard(projectIdx: Int, userIdx: Int) : ProjectNameWithScrapedCardCount?

//}