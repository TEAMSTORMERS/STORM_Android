package com.stormers.storm.project.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.project.model.ProjectsData
import com.stormers.storm.project.viewholder.ProjectsViewHolder

class ProjectsAdapter () : BaseAdapter<ProjectsData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        return ProjectsViewHolder(parent)
    }

}