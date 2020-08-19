package com.stormers.storm.project.network.response

import com.stormers.storm.project.model.ProjectLookupModel

data class ResponseLookupProject(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ProjectLookupModel
)