package com.stormers.storm.project.model

data class ProjectPreviewModel(
    val projectIdx: Int,

    val projectName: String,

    val projectContents: List<String>
)