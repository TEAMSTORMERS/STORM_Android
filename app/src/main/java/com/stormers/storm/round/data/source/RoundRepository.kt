package com.stormers.storm.round.data.source

import com.stormers.storm.round.model.RoundDescriptionModel

class RoundRepository(
    val roundsLocalDataSource: RoundDataSource,
    val roundsRemoteDataSource: RoundDataSource
) : RoundDataSource {

    companion object {
        private const val TAG = "RoundRepository"

        private var instance: RoundRepository? = null

        @JvmStatic fun getInstance(roundsLocalDataSource: RoundDataSource,
                                   roundsRemoteDataSource: RoundDataSource
        ): RoundRepository {
            return instance ?: RoundRepository(roundsLocalDataSource, roundsRemoteDataSource)
                .apply { instance = this }
        }
    }

    override fun getRoundsInfo(
        projectIdx: Int,
        userIdx: Int,
        callback: RoundDataSource.LoadRoundsCallback<RoundDescriptionModel>
    ) {
        roundsLocalDataSource.getRoundsInfo(projectIdx, userIdx, object : RoundDataSource.LoadRoundsCallback<RoundDescriptionModel> {
            override fun onRoundsLoaded(rounds: List<RoundDescriptionModel>) {
                callback.onRoundsLoaded(rounds)
            }

            override fun onDataNotAvailable() {
                getRoundsInfoFromRemote(projectIdx, userIdx, callback)
            }
        })
    }

    private fun getRoundsInfoFromRemote(
        projectIdx: Int,
        userIdx: Int,
        callback: RoundDataSource.LoadRoundsCallback<RoundDescriptionModel>
    ) {
        roundsRemoteDataSource.getRoundsInfo(projectIdx, userIdx, object: RoundDataSource.LoadRoundsCallback<RoundDescriptionModel> {
            override fun onRoundsLoaded(rounds: List<RoundDescriptionModel>) {
                callback.onRoundsLoaded(rounds)
                refreshLocalRoundsInfo(rounds)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshLocalRoundsInfo(rounds: List<RoundDescriptionModel>) {
        roundsLocalDataSource.saveRoundsInfo(rounds)
    }

    override fun saveRoundsInfo(roundsInfo: List<RoundDescriptionModel>) {
        //Not required
    }
}
