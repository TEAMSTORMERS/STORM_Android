package com.stormers.storm.project.model

import com.stormers.storm.round.model.RoundModel
import com.stormers.storm.user.User

data class ProjectModel(
    val projectIdx: Int,

    val projectDate: String,

    val projectCode: String?,

    var projectName: String?,

    var projectComment: String?,

    var projectRounds: List<RoundModel>?,

    val projectParticipants: List<User>?
)