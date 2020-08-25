package com.stormers.storm.project.data.source.local

import com.stormers.storm.card.data.source.local.CardDao
import com.stormers.storm.card.data.Card
import com.stormers.storm.project.data.Project
import com.stormers.storm.project.data.source.ProjectsDataSource
import com.stormers.storm.project.model.ProjectDetailInfo
import com.stormers.storm.project.model.ProjectPreviewModel
import com.stormers.storm.user.UserDao
import com.stormers.storm.util.AppExecutors

class ProjectsLocalDataSource private constructor(
    val appExecutors: AppExecutors,
    val projectDao: ProjectDao,
    val cardDao: CardDao,
    val userDao: UserDao
) : ProjectsDataSource {

    //Todo: CardDao 를 거치지 않고 쿼리 하나로 카드까지 받아올 수 있도록 수정해야함.
    override fun getProjectPreviews(userIdx: Int, callback: ProjectsDataSource.LoadProjectsCallback<ProjectPreviewModel>) {
        appExecutors.diskIO.execute {
            val projectPreviews = projectDao.getProjectPreviews(userIdx)
            for (projectPreview in projectPreviews) {
                projectPreview.projectCardPreview =
                    cardDao.getCardPreviews(projectPreview.projectIdx, 4)
            }
            appExecutors.mainThread.execute {
                if (projectPreviews.isEmpty()) {
                    callback.onDataNotAvailable()
                } else {
                    callback.onProjectsLoaded(projectPreviews)
                }
            }
        }
    }

    override fun getProjectDetailInfo(projectIdx: Int, callback: ProjectsDataSource.GetProjectCallback<ProjectDetailInfo>) {
        appExecutors.diskIO.execute {
            val project = projectDao.getProject(projectIdx)
            project?.let {
                it.roundCount = projectDao.getRoundCount(projectIdx)
                it.projectParticipantsList = userDao.getParticipants(projectIdx)
            }

            appExecutors.mainThread.execute {
                if (project == null || project.projectParticipantsList.isEmpty()) {
                    callback.onDataNotAvailable()
                } else {
                    callback.onProjectLoaded(project)
                }
            }
        }
    }

    override fun saveProjectPreviews(projectPreviewModels: List<ProjectPreviewModel>) {
        for (projectPreviewModel in projectPreviewModels) {
            projectPreviewModel.run {
                val project = Project(projectIdx, null, projectName, null, null)
                val cards = List(projectCardPreview.size) { i ->
                    projectCardPreview.let {
                        Card(
                            it[i].cardIdx,
                            projectIdx,
                            null,
                            null,
                            it[i].cardImage,
                            it[i].cardText,
                            null
                        )
                    }
                }
                appExecutors.diskIO.execute {
                    projectDao.insert(project)
                    cardDao.insert(cards)
                }
            }
        }
    }

    override fun saveProjectDetailInfo(projectDetailInfo: ProjectDetailInfo) {
//        appExecutors.diskIO.execute {
//            projectDetailInfo.run {
//                var project = projectDao.get(projectIdx)
//                if (project != null) {
//                    project.projectName = projectName
//                    project.projectDate = projectDate
//                    projectDao.update(project)
//                } else {
//                    project = Project(projectIdx, projectDate, projectName, null, null)
//                    projectDao.insert(project)
//                }
//            }
//        }
        //아직 사용할 수 없음.
        //Todo: 참가자 정보를 디비에 저장할 수 있도록 해야 함.
    }
}