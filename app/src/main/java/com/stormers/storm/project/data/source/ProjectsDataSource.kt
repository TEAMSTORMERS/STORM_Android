package com.stormers.storm.project.data.source

import com.stormers.storm.project.model.ProjectDetailInfo
import com.stormers.storm.project.model.ProjectPreviewModel

interface ProjectsDataSource {

    interface LoadProjectsCallback<T> {

        fun onProjectsLoaded(projects: List<T>)

        fun onDataNotAvailable()
    }

    interface GetProjectCallback<T> {

        fun onProjectLoaded(project: T)

        fun onDataNotAvailable()
    }

    fun getProjectPreviews(userIdx: Int, callback: LoadProjectsCallback<ProjectPreviewModel>)

    fun getProjectDetailInfo(projectIdx: Int, callback: GetProjectCallback<ProjectDetailInfo>)

    fun saveProjectPreviews(projectPreviewModels: List<ProjectPreviewModel>)

    fun saveProjectDetailInfo(projectDetailInfo: ProjectDetailInfo)
}