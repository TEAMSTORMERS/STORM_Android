package com.stormers.storm.round

import androidx.room.Dao
import androidx.room.Query
import com.stormers.storm.base.BaseDao
import com.stormers.storm.round.model.RoundModel

@Dao
abstract class RoundDao : BaseDao<RoundModel> {

    @Query("SELECT * FROM round_entity WHERE project_idx = :projectIdx")
    abstract fun getAll(projectIdx: Int): List<RoundModel>?

    @Query("SELECT * FROM round_entity WHERE round_idx = :roundIdx")
    abstract fun get(roundIdx: Int): RoundModel?

    @Query("DELETE FROM round_entity")
    abstract fun deleteAll()
}