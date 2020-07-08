package com.stormers.storm.project

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter

class ProjectsAdapter () : BaseAdapter<ProjectsData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        return ProjectsViewHolder(parent)
    }

}