package com.stormers.storm.round.model

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

class RoundDescriptionModel (
    //Todo: primitive 자료형으로 변경한 뒤, getter setter 등으로 데이터 가공할 수 있도록 변경
    @Ignore
    val projectTitle : String,
    @SerializedName("round_number")
    val roundNo : Int,
    @SerializedName("round_purpose")
    val roundGoal : String,
    @SerializedName("round_time")
    val time : Int,
    @SerializedName("round_participant")
    val round_participant : List<RoundData>,
    @SerializedName("round_idx")
    val roundIdx : Int,
    @Ignore
    val projectIdx: Int
)