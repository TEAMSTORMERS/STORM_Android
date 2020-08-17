package com.stormers.storm.round

import android.util.Log
import com.stormers.storm.round.model.RoundModel
import com.stormers.storm.ui.GlobalApplication

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

    private var projectIdxCache: Int? = null

    private var cache: List<RoundModel>? = null

    fun getAll(projectIdx: Int, callback: LoadRoundsCallback) {
        if (projectIdxCache == null || projectIdxCache != projectIdx || cache == null) {
            projectIdxCache = projectIdx
            cache = dao.getAll(projectIdx)
        }
        val result = cache

        Log.d(TAG, "projectIdx : $projectIdx result : $result")

        if (result == null) {
            callback.onDataNotAvailable()
        } else {
            callback.onRoundsLoaded(result)
        }
    }

    fun get(roundIdx: Int, callback: GetRoundCallback) {
        var result: RoundModel? = null

        if (cache != null) {
            for (round in cache!!) {
                if (round.roundIdx == roundIdx) {
                    result = round
                    break
                }
            }
        } else {
            result = dao.get(roundIdx)
            result?.let {
                cache = List(1) { i -> it}
            }

        }

        Log.d(TAG, "get: roundIdx: $roundIdx round : $result")

        if (result == null) {
            callback.onDataNotAvailable()
        } else {
            callback.onRoundLoaded(result)
        }
    }

    fun insert(round: RoundModel) {
        dao.insert(round)
        cache = List(1) {round}
        projectIdxCache = null
        Log.d(TAG, "insert: $round")
    }

    fun update(round: RoundModel) {
        dao.update(round)
        cache = List(1) {round}
        projectIdxCache = null
        Log.d(TAG, "insert: $round")
    }

    fun deleteAll() {
        dao.deleteAll()
        cache = null
        projectIdxCache = null
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