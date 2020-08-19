package com.stormers.storm.project

import android.util.Log
import com.stormers.storm.card.repository.CardRepository
import com.stormers.storm.project.model.ProjectEntity
import com.stormers.storm.project.model.ProjectModel
import com.stormers.storm.project.model.ProjectPreviewModel
import com.stormers.storm.round.RoundRepository
import com.stormers.storm.ui.GlobalApplication

class ProjectRepository {

    companion object {
        private const val TAG = "ProjectRepository"

        private var instance: ProjectRepository? = null

        fun getInstance(): ProjectRepository {
            if (instance == null) {
                synchronized(ProjectRepository::class.java) {
                    if (instance == null) {
                        instance = ProjectRepository()
                    }
                }
            }
            return instance!!
        }
    }

    private val projectParticipantRepository: ProjectParticipantRepository by lazy { ProjectParticipantRepository.getInstance() }

    private val roundRepository: RoundRepository by lazy { RoundRepository.getInstance() }

    private val cardRepository: CardRepository by lazy { CardRepository.getInstance() }

    private val dao: ProjectDao by lazy { GlobalApplication.databaseManager.projectDao() }


    fun getAll(callback: LoadProjectsCallback) {
        val results = getAll()

        if (results == null) {
            callback.onDataNotAvailable()
        } else {
            callback.onProjectsLoaded(getProjectModels(results))
        }
    }

    private fun getAll(): List<ProjectEntity>? {
        val results = dao.getAll()
        Log.d(TAG, "getAll(): $results")
        return results
    }

    fun get(projectIdx: Int, callback: GetProjectCallback) {
        val result = dao.get(projectIdx)

        Log.d(TAG, "get($projectIdx): $result")

        if (result == null) {
            callback.onDataNotAvailable()
        } else {
            callback.onProjectLoaded(getProjectModel(result))
        }
    }

    fun getPreviewAll(callback: LoadProjectPreviewCallback) {
        val results = getAll()

        if (results == null || results.isEmpty()) {
            callback.onDataNotAvailable()
        } else {
            val projectPreviewModels = List(results.size) { i ->
                val cardList = cardRepository.getContentsAll(results[i].projectIdx, 4)
                ProjectPreviewModel(results[i].projectIdx, results[i].projectName!!, cardList)
            }
            callback.onPreviewLoaded(projectPreviewModels)
        }
    }

    fun insert(projectModel: ProjectModel) {
        projectModel.let {
            val projectEntity = ProjectEntity(it.projectIdx, it.projectName, it.projectCode, it.projectComment)

            dao.insert(projectEntity)
            Log.d(TAG, "insert: $projectEntity")
        }
    }

    private fun getProjectModel(projectEntity: ProjectEntity): ProjectModel {
        projectEntity.let {
            val rounds = roundRepository.getAll(it.projectIdx)
            val participants = projectParticipantRepository.getAll(it.projectIdx)

            return ProjectModel(it.projectIdx, it.projectCode, it.projectName, it.projectComment, rounds, participants)
        }
    }

    private fun getProjectModels(projectEntities: List<ProjectEntity>): List<ProjectModel> {
        return List(projectEntities.size) { i -> getProjectModel(projectEntities[i])}
    }

    interface LoadProjectPreviewCallback {
        fun onPreviewLoaded(projects: List<ProjectPreviewModel>)

        fun onDataNotAvailable()
    }

    interface LoadProjectsCallback {
        fun onProjectsLoaded(projects: List<ProjectModel>)

        fun onDataNotAvailable()
    }

    interface GetProjectCallback {
        fun onProjectLoaded(project: ProjectModel)

        fun onDataNotAvailable()
    }
}