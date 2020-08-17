package com.stormers.storm.user

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_entity")
data class UserModel(
    @SerializedName("user_idx")
    val userIdx: Int,
    @SerializedName("user_img")
    val UserImg: String,
    @SerializedName("user_name")
    val userName: String
)
