package com.stormers.storm.project.model

import com.google.gson.annotations.SerializedName
import com.stormers.storm.card.model.SimpleCardModel

data class ParticipatedProjectModel(

    @SerializedName("project_idx")
    val projectIdx: Int,

    @SerializedName("project_name")
    val projectName: String,

    @SerializedName("card_list")
    val projectCard: List<SimpleCardModel>
)