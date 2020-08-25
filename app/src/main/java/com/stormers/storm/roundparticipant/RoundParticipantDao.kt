package com.stormers.storm.roundparticipant

import androidx.room.Dao
import androidx.room.Query
import com.stormers.storm.base.BaseDao
import com.stormers.storm.round.data.RoundParticipant

@Dao
abstract class RoundParticipantDao: BaseDao<RoundParticipant> {

    @Query("SELECT user_idx FROM round_participant_entity WHERE round_idx = :roundIdx")
    abstract fun getAll(roundIdx: Int): List<Int>?
}