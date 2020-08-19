package com.stormers.storm.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_entity")
data class UserModel(
    @SerializedName("user_idx")
    @PrimaryKey
    @ColumnInfo(name = "user_idx")
    val userIdx: Int,

    @SerializedName("user_img")
    @ColumnInfo(name = "user_img")
    val userImg: String,

    @SerializedName("user_name")
    @ColumnInfo(name = "user_name")
    val userName: String
) {
    override fun toString(): String {
        return "userIdx: $userIdx, userImg: $userImg, userName: $userName"
    }
}
