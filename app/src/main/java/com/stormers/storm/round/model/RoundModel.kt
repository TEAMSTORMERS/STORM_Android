package com.stormers.storm.round.model

import com.stormers.storm.user.UserModel

data class RoundModel(
    val roundIdx: Int,

    val roundNumber: Int?,

    val roundPurpose: String?,

    val roundTime: Int?,

    var participants: List<UserModel>?
)