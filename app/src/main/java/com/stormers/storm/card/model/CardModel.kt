package com.stormers.storm.card.model

import com.stormers.storm.user.UserModel

data class CardModel(
    val url: String,
    var isLiked: Boolean = false,
    val owner: UserModel?
    /*
    val roundCount : String,
    val roundGoal : String,
    val roundTime : String*/
)