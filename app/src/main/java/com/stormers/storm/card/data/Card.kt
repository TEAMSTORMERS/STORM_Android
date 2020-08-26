package com.stormers.storm.card.data

//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import androidx.room.ForeignKey
//import com.stormers.storm.project.data.Project

//@Entity(
//    tableName = "card_entity",
//    primaryKeys = ["card_idx"],
//    foreignKeys = [
//        ForeignKey(
//            entity = Project::class,
//            parentColumns = ["project_idx"],
//            childColumns = ["card_idx"],
//            onDelete = ForeignKey.CASCADE
//        )]
//)
//data class Card(
//    @ColumnInfo(name = "card_idx")
//    val cardIdx: Int,
//
//    @ColumnInfo(name = "project_idx")
//    val projectIdx: Int,
//
//    @ColumnInfo(name = "round_idx")
//    val roundIdx: Int?,
//
//    @ColumnInfo(name = "user_idx")
//    val userIdx: Int?,
//
//    @ColumnInfo(name = "card_img")
//    val cardImage: String?,
//
//    @ColumnInfo(name = "card_txt")
//    val cardText: String?,
//
//    @ColumnInfo(name = "memo")
//    var memo: String?
//) {
//
//    override fun toString(): String {
//        return "cardIdx: $cardIdx, projectIdx: $projectIdx, roundIdx: $roundIdx, " +
//                "cardImage: $cardImage, cardText: ${cardText}, memo: $memo"
//    }
//}