package com.stormers.storm.round.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.stormers.storm.user.User

class RoundDescriptionModel (
    @SerializedName("round_idx")
    @ColumnInfo(name = "round_idx")
    val roundIdx : Int,
    @SerializedName("round_number")
    @ColumnInfo(name = "round_number")
    val roundNumber : Int,
    @SerializedName("round_purpose")
    val roundPurpose : String,
    @SerializedName("round_time")
    val roundTime : Int,
    @SerializedName("round_participant")
    var roundParticipant : List<User>
)