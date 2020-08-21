package com.stormers.storm.round

import android.util.Log
import com.stormers.storm.project.model.ProjectEntity
import com.stormers.storm.round.model.RoundEntity
import com.stormers.storm.round.model.RoundModel
import com.stormers.storm.roundparticipant.RoundParticipantRepository
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.user.UserModel

class RoundRepository {

    companion object {
        private const val TAG = "RoundRepository"

        private var instance: RoundRepository? = null

        fun getInstance(): RoundRepository {
            if (instance == null) {
                synchronized(RoundRepository::class.java) {
                    if (instance == null) {
                        instance = RoundRepository()
                    }
                }
            }
            return instance!!
        }
    }

    private val dao: RoundDao by lazy { GlobalApplication.databaseManager.roundDao() }

    //private val projectRepository: ProjectRepository by lazy { ProjectRepository }

    private val roundParticipantRepository: RoundParticipantRepository by lazy { RoundParticipantRepository.getInstance() }

    fun getAll(projectIdx: Int, callback: LoadRoundsCallback) {

        val results = dao.getAll(projectIdx)

        Log.d(TAG, "projectIdx : $projectIdx result : $results")

        if (results == null || results.isEmpty()) {
            callback.onDataNotAvailable()
        } else {
            callback.onRoundsLoaded(getRoundModels(results))
        }
    }

    fun getAll(projectIdx: Int): List<RoundModel> {
        val results = dao.getAll(projectIdx)

        Log.d(TAG, "projectIdx : $projectIdx result : $results")

        return if (results == null) {
            mutableListOf()
        } else {
            getRoundModels(results)
        }
    }

    fun get(roundIdx: Int, callback: GetRoundCallback) {
        val result = dao.get(roundIdx)

        Log.d(TAG, "get: roundIdx: $roundIdx round : $result")

        if (result == null) {
            callback.onDataNotAvailable()
        } else {
            callback.onRoundLoaded(getRoundModel(result))
        }
    }

    fun insert(projectIdx: Int, round: RoundModel) {
        roundParticipantRepository.insert(projectIdx, round.roundIdx, round.participants!!)

        round.let {
            dao.insert(RoundEntity(it.roundIdx, it.roundNumber, it.roundPurpose, it.roundTime, projectIdx))
        }
        Log.d(TAG, "insert: $round")
    }

    fun update(round: RoundEntity) {
        dao.update(round)
        Log.d(TAG, "insert: $round")
    }

    fun deleteAll() {
        dao.deleteAll()
    }

    private fun getRoundModels(roundEntities: List<RoundEntity>): List<RoundModel> {
        val roundModels = mutableListOf<RoundModel>()
        for (roundEntity in roundEntities) {
            roundModels.add(getRoundModel(roundEntity))
        }

        return roundModels
    }

    private fun getRoundModel(roundEntity: RoundEntity): RoundModel {
        var roundModel: RoundModel

        var participants: List<UserModel>


        roundEntity.let {
            participants = roundParticipantRepository.getAll(it.roundIdx)
            roundModel = RoundModel(it.roundIdx, it.roundNumber, it.roundPurpose, it.roundTime, participants)
        }
        return roundModel
    }

    interface LoadRoundsCallback {

        fun onRoundsLoaded(rounds: List<RoundModel>)

        fun onDataNotAvailable()
    }

    interface GetRoundCallback {

        fun onRoundLoaded(round: RoundModel)

        fun onDataNotAvailable()
    }
}