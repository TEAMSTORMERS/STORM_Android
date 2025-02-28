package com.stormers.storm.project.network.response

import com.stormers.storm.project.model.ProjectDataModel

data class ResponseAddProject(
    val status:Int,
    val success: Boolean,
    val message: String,
    val data: ProjectDataModel
)