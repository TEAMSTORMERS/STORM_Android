package com.stormers.storm.roundparticipant

import androidx.room.Dao
import androidx.room.Query
import com.stormers.storm.base.BaseDao

@Dao
abstract class RoundParticipantDao: BaseDao<RoundParticipantEntity> {

    @Query("SELECT user_idx FROM round_participant_entity WHERE round_idx = :roundIdx")
    abstract fun getAll(roundIdx: Int): List<Int>?
}