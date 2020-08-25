package com.stormers.storm.project.model

import androidx.room.ColumnInfo

data class ProjectNameWithScrapedCardCount(
    @ColumnInfo(name = "project_name")
    val projectName: String,

    @ColumnInfo(name = "scrap_count")
    val scrapCount: Int
)