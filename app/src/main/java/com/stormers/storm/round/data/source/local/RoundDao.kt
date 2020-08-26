package com.stormers.storm.round.data.source.local

//import androidx.room.Dao
//import androidx.room.Query
//import com.stormers.storm.base.BaseDao
//import com.stormers.storm.round.data.Round
//import com.stormers.storm.round.model.RoundDescriptionModel
//import com.stormers.storm.round.model.RoundInfoModel
//
//@Dao
//abstract class RoundDao : BaseDao<Round> {

//    @Query("SELECT * FROM round_entity WHERE project_idx = :projectIdx")
//    abstract fun getAll(projectIdx: Int): List<Round>?
//
//    @Query("SELECT * FROM round_entity WHERE round_idx = :roundIdx")
//    abstract fun get(roundIdx: Int): Round?
//
//    @Query("SELECT round_idx, round_number, round_purpose, round_time FROM round_entity WHERE project_idx = :projectIdx AND round_idx in (SELECT round_idx FROM round_participant_entity AS rp JOIN user_entity ON user_entity.user_idx = rp.user_idx WHERE rp.user_idx = :userIdx)")
//    abstract fun getRoundInfo(projectIdx: Int, userIdx: Int): List<RoundDescriptionModel>
//
//    @Query("SELECT round_number, round_purpose, round_time FROM round_entity WHERE round_idx in (SELECT round_idx from card_entity WHERE card_idx = :cardIdx)")
//    abstract fun getRoundInfoOfScrapedCard(cardIdx: Int): RoundInfoModel
//
//    @Query("DELETE FROM round_entity")
//    abstract fun deleteAll()

//}