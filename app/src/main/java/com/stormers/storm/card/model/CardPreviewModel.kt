package com.stormers.storm.card.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class CardPreviewModel (
    @ColumnInfo(name = "card_idx")
    val cardIdx: Int,

    @ColumnInfo(name = "card_img")
    @SerializedName("card_img")
    val cardImage: String?,

    @ColumnInfo(name = "card_txt")
    @SerializedName("card_txt")
    val cardText: String?
)
