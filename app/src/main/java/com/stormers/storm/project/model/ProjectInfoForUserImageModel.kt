package com.stormers.storm.project.model

data class ProjectInfoForUserImageModel(
    val project_name : String,
    val project_date : String,
    val round_count : Int,
    val project_participants_list : List<String>
)