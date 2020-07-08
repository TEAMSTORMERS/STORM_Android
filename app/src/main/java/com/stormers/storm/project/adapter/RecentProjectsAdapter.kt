package com.stormers.storm.project.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.project.model.RecentProjectsModel
import com.stormers.storm.project.viewholder.ProjectsViewHolder

class RecentProjectsAdapter () : BaseAdapter<RecentProjectsModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        return ProjectsViewHolder(parent)
    }

}