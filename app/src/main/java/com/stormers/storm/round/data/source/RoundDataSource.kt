package com.stormers.storm.round.data.source

import com.stormers.storm.round.model.RoundDescriptionModel

interface RoundDataSource {

    interface LoadRoundsCallback<T> {

        fun onRoundsLoaded(rounds: List<T>)

        fun onDataNotAvailable()
    }

    interface GetRoundCallback<T> {

        fun onRoundLoaded(round: T)

        fun onDataNotAvailable()
    }

    fun getRoundsInfo(projectIdx: Int, userIdx: Int, callback: LoadRoundsCallback<RoundDescriptionModel>)

    fun saveRoundsInfo(roundsInfo: List<RoundDescriptionModel>)
}