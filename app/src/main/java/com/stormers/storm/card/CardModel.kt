package com.stormers.storm.card

import com.stormers.storm.user.UserModel

data class CardModel(
    val url: String,
    var isLiked: Boolean = false,
    val owner: UserModel?
)