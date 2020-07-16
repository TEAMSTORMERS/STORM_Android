package com.stormers.storm.project.model

data class ProjectData (
    val project_name : String,
    val project_date : String,
    val round_count : Int,
    val project_participants_list : List<String>
)