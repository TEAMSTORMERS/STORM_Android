package com.stormers.storm.round

import androidx.room.Dao
import androidx.room.Query
import com.stormers.storm.base.BaseDao
import com.stormers.storm.round.model.RoundEntity

@Dao
abstract class RoundDao : BaseDao<RoundEntity> {

    @Query("SELECT * FROM round_entity WHERE project_idx = :projectIdx")
    abstract fun getAll(projectIdx: Int): List<RoundEntity>?

    @Query("SELECT * FROM round_entity WHERE round_idx = :roundIdx")
    abstract fun get(roundIdx: Int): RoundEntity?

    @Query("DELETE FROM round_entity")
    abstract fun deleteAll()
}