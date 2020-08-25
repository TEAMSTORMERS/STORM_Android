package com.stormers.storm.project.network.response

import com.stormers.storm.project.model.ProjectPreviewModel

data class ResponseProjectPreviews(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<ProjectPreviewModel>
)