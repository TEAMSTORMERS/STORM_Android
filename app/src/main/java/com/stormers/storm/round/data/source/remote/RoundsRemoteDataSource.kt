package com.stormers.storm.round.data.source.remote

import android.util.Log
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.round.data.source.RoundDataSource
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.round.network.RequestRound
import com.stormers.storm.round.network.response.ResponseFinalRoundData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RoundsRemoteDataSource : RoundDataSource {

    private const val TAG = "RoundsRemoteDataSource"

    private val requestRound: RequestRound by lazy { RetrofitClient.create(RequestRound::class.java) }

    override fun getRoundsInfo(
        projectIdx: Int,
        userIdx: Int,
        callback: RoundDataSource.LoadRoundsCallback<RoundDescriptionModel>
    ) {
        requestRound.getRoundInfo(projectIdx, userIdx).enqueue(object : Callback<ResponseFinalRoundData> {

            override fun onFailure(call: Call<ResponseFinalRoundData>, t: Throwable) {
                Log.d(TAG, "getRoundsInfo: projectIdx : $projectIdx, userIdx: $userIdx")
            }

            override fun onResponse(
                call: Call<ResponseFinalRoundData>,
                response: Response<ResponseFinalRoundData>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d(TAG, "getRoundsInfo: Success")
                        callback.onRoundsLoaded(response.body()!!.data)

                    } else {
                        Log.d(TAG, "getRoundsInfo: Not success, ${response.body()!!.message}")
                        callback.onDataNotAvailable()
                    }
                } else {
                    Log.d(TAG, "getRoundsInfo: Not success ful, ${response.message()}")
                    callback.onDataNotAvailable()
                }
            }
        })
    }

    override fun saveRoundsInfo(roundsInfo: List<RoundDescriptionModel>) {
        //Not required
    }
}