package com.stormers.storm.card.model

import android.content.Context
import com.stormers.storm.user.UserModel

class CardModel(
    val url: String,
    var isLiked: Boolean = false,
    val owner: UserModel?
) {

    fun getImageID(context: Context): Int {
     return context.resources.getIdentifier(url, "drawable", context.packageName)
    }
}