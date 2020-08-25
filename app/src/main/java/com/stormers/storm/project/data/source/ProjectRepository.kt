package com.stormers.storm.project.data.source

import com.stormers.storm.project.model.ProjectPreviewModel
import com.stormers.storm.project.data.source.local.ProjectsLocalDataSource
import com.stormers.storm.project.data.source.remote.ProjectsRemoteDataSource
import com.stormers.storm.project.model.ProjectDetailInfo

class ProjectRepository (
    val projectsRemoteDataSource: ProjectsDataSource,
    val projectsLocalDataSource: ProjectsDataSource) : ProjectsDataSource {

    companion object {
        private const val TAG = "ProjectRepository"

        private var instance: ProjectRepository? = null

        @JvmStatic fun getInstance(projectsRemoteDataSource: ProjectsRemoteDataSource,
                                   projectsLocalDataSource: ProjectsLocalDataSource): ProjectRepository {
            return instance ?: ProjectRepository(projectsRemoteDataSource, projectsLocalDataSource)
                .apply { instance = this }
        }
    }

    override fun getProjectPreviews(
        userIdx: Int,
        callback: ProjectsDataSource.LoadProjectsCallback<ProjectPreviewModel>
    ) {
        projectsLocalDataSource.getProjectPreviews(userIdx, object: ProjectsDataSource.LoadProjectsCallback<ProjectPreviewModel> {
            override fun onProjectsLoaded(projects: List<ProjectPreviewModel>) {
                callback.onProjectsLoaded(projects)
            }

            override fun onDataNotAvailable() {
                getProjectPreviewsFromRemote(userIdx, callback)
            }
        })
    }

    override fun getProjectDetailInfo(
        projectIdx: Int,
        callback: ProjectsDataSource.GetProjectCallback<ProjectDetailInfo>
    ) {
        projectsLocalDataSource.getProjectDetailInfo(projectIdx, object: ProjectsDataSource.GetProjectCallback<ProjectDetailInfo> {
            override fun onProjectLoaded(project: ProjectDetailInfo) {
                callback.onProjectLoaded(project)
            }

            override fun onDataNotAvailable() {
               getProjectDetailInfoFromRemote(projectIdx, callback)
            }
        })
    }

    private fun getProjectPreviewsFromRemote(
        userIdx: Int,
        callback: ProjectsDataSource.LoadProjectsCallback<ProjectPreviewModel>)
    {
        projectsRemoteDataSource.getProjectPreviews(userIdx, object: ProjectsDataSource.LoadProjectsCallback<ProjectPreviewModel> {
            override fun onProjectsLoaded(projects: List<ProjectPreviewModel>) {
                callback.onProjectsLoaded(projects)
                refreshLocalProjectPreviews(projects)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun getProjectDetailInfoFromRemote(
        projectIdx: Int,
        callback: ProjectsDataSource.GetProjectCallback<ProjectDetailInfo>
    ) {
        projectsRemoteDataSource.getProjectDetailInfo(projectIdx, object: ProjectsDataSource.GetProjectCallback<ProjectDetailInfo> {
            override fun onProjectLoaded(project: ProjectDetailInfo) {
                callback.onProjectLoaded(project)
                refreshLocalProjectDetailInfo(project)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshLocalProjectDetailInfo(project: ProjectDetailInfo) {
        projectsLocalDataSource.saveProjectDetailInfo(project)
    }

    private fun refreshLocalProjectPreviews(projects: List<ProjectPreviewModel>) {
        projectsLocalDataSource.saveProjectPreviews(projects)
    }

    override fun saveProjectPreviews(projectPreviewModels: List<ProjectPreviewModel>) {
        //Not required
    }

    override fun saveProjectDetailInfo(projectDetailInfo: ProjectDetailInfo) {
        //Not required
    }
}