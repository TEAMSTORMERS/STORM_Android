package com.stormers.storm.round.data.source.local

import com.stormers.storm.round.data.source.RoundDataSource
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.user.UserDao
import com.stormers.storm.util.AppExecutors

class RoundsLocalDataSource private constructor(
//    val appExecutors: AppExecutors,
//    val roundDao: RoundDao,
//    val userDao: UserDao
) : RoundDataSource {

    override fun getRoundsInfo(
        projectIdx: Int,
        userIdx: Int,
        callback: RoundDataSource.LoadRoundsCallback<RoundDescriptionModel>
    ) {
//        appExecutors.diskIO.execute {
//            val roundInfoList = roundDao.getRoundInfo(projectIdx, userIdx)
//            for (roundInfo in roundInfoList) {
//                roundInfo.roundParticipant =
//                    userDao.getParticipant(projectIdx, roundInfo.roundNumber)
//            }
//            appExecutors.mainThread.execute {
//                if (roundInfoList.isEmpty()) {
//                    callback.onDataNotAvailable()
//                } else {
//                    callback.onRoundsLoaded(roundInfoList)
//                }
//            }
//        }
        callback.onDataNotAvailable()
    }

    override fun saveRoundsInfo(roundsInfo: List<RoundDescriptionModel>) {
        //아직은 사용할 수 없음.
        //Todo: 서버에게 라운드별 참가자 목록에서 userIdx도 함께 반환해달라고 해야함
        //https://github.com/TEAMSTORMERS/STORM_Server/wiki/%5BGET%5D-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EB%B3%84-%EC%A0%95%EB%B3%B4-%EC%A1%B0%ED%9A%8C
    }
}