package com.stormers.storm.roundparticipant

import android.util.Log
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.user.UserModel
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

    fun getAll(roundIdx: Int) : List<UserModel> {
        val result = dao.getAll(roundIdx)
        Log.d(TAG, "getAll : $result")

        return if (result == null) {
            mutableListOf()
        } else {
            userRepository.getAll(result)
        }
    }

    fun insert(roundIdx: Int, participants: List<UserModel>) {
        for (participant in participants) {
            dao.insert(RoundParticipantEntity(roundIdx, participant.userIdx))
            userRepository.insert(participant)
        }
    }
}