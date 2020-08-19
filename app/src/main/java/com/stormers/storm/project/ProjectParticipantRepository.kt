package com.stormers.storm.project

import android.util.Log
import com.stormers.storm.project.model.ProjectParticipantEntity
import com.stormers.storm.roundparticipant.RoundParticipantRepository
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.user.UserModel
import com.stormers.storm.user.UserRepository

class ProjectParticipantRepository {

    companion object {
        private const val TAG = "ProjectParticipantRepo"

        private var instance: ProjectParticipantRepository? = null

        fun getInstance(): ProjectParticipantRepository {
            if (instance == null) {
                synchronized(ProjectParticipantRepository::class.java) {
                    if (instance == null) {
                        instance = ProjectParticipantRepository()
                    }
                }
            }
            return instance!!
        }
    }

    private val dao: ProjectParticipantDao by lazy { GlobalApplication.databaseManager.projectParticipantDao() }

    private val userRepository: UserRepository by lazy { UserRepository.getInstance() }

    fun getAll(projectIdx: Int): List<UserModel> {
        val results = dao.getAll(projectIdx)
        Log.d(TAG, "getAll : result")

        return if (results == null) {
            mutableListOf()
        } else {
            userRepository.getAll(results)
        }
    }

    fun insert(projectIdx: Int, participants: List<UserModel>) {
        val previousParticipantIdx = dao.getAll(projectIdx)

        for (participant in participants) {
            if (previousParticipantIdx != null && previousParticipantIdx.contains(participant.userIdx)) {
                continue
            }
            dao.insert(ProjectParticipantEntity(projectIdx, participant.userIdx))
            Log.d(TAG, "insert: projectIdx: $projectIdx, user: $participant")
        }
    }
}