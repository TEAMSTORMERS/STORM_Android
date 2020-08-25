package com.stormers.storm.roundparticipant

import android.util.Log
import com.stormers.storm.project.ProjectParticipantRepository
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.user.User
import com.stormers.storm.user.UserRepository

class RoundParticipantRepository  {

    companion object {
        private const val TAG = "RoundParticipantRepo"

        private var instance: RoundParticipantRepository? = null

        fun getInstance(): RoundParticipantRepository {
            if (instance == null) {
                synchronized(RoundParticipantRepository::class.java) {
                    if (instance == null) {
                        instance = RoundParticipantRepository()
                    }
                }
            }
            return instance!!
        }
    }

    private val dao: RoundParticipantDao by lazy { GlobalApplication.databaseManager.roundParticipantDao() }

    private val userRepository: UserRepository by lazy { UserRepository.getInstance() }

    private val projectParticipantRepository: ProjectParticipantRepository by lazy { ProjectParticipantRepository.getInstance() }

    fun getAll(roundIdx: Int) : List<User> {
        val result = dao.getAll(roundIdx)
        Log.d(TAG, "getAll : $result")

        return if (result == null) {
            mutableListOf()
        } else {
            userRepository.getAll(result)
        }
    }

    fun insert(projectIdx: Int, roundIdx: Int, participants: List<User>) {
        for (participant in participants) {
            dao.insert(RoundParticipantEntity(roundIdx, participant.userIdx))
            //유저 정보에 삽입
            userRepository.insert(participant)

            Log.d(TAG, "insert: projectIdx: $projectIdx, user: $participant")
        }
        //프로젝트 참가자 정보에도 삽입
        projectParticipantRepository.insert(projectIdx, participants)
    }
}