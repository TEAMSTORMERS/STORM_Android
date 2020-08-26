package com.stormers.storm.user

//import androidx.room.Dao
//import androidx.room.Query
//import com.stormers.storm.base.BaseDao
//
//@Dao
//abstract class UserDao: BaseDao<User> {
//
//    @Query("SELECT * FROM user_entity WHERE user_idx = :userIdx")
//    abstract fun get(userIdx: Int): User?
//
//    @Query("SELECT user_img FROM user_entity Join project_participant_entity WHERE project_idx = :projectIdx")
//    abstract fun getParticipants(projectIdx: Int): List<String>
//
//    @Query("select user_name, user_img FROM user_entity u JOIN round_participant_entity rp ON u.user_idx = rp.user_idx JOIN round_entity r ON r.round_idx = rp.round_idx JOIN project_entity p ON p.project_idx = r.project_idx WHERE r.project_idx = :projectIdx AND r.round_number = :roundNumber")
//    abstract fun getParticipant(projectIdx: Int, roundNumber: Int) : List<ParticipantEnumModel>
//}