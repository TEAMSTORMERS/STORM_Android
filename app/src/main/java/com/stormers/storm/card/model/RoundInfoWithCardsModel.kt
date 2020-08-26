package com.stormers.storm.card.model

import com.google.gson.annotations.SerializedName

data class RoundInfoWithCardsModel(
    @SerializedName("project_name")
    val projectName: String,

    @SerializedName("round_number")
    val roundNumber: Int,

    @SerializedName("round_purpose")
    val roundPurpose: String,

    @SerializedName("round_time")
    val roundTime: Int,

    @SerializedName("card_list")
    val cardWithOwnerList: List<CardWithOwnerModel>
)