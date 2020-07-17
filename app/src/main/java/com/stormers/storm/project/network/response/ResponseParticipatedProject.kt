package com.stormers.storm.project.network.response

import com.stormers.storm.project.model.ParticipatedProjectModel

data class ResponseParticipatedProject(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<ParticipatedProjectModel>
)