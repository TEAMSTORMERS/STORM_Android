package com.stormers.storm.project.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.project.model.ProjectPreviewModel
import com.stormers.storm.project.viewholder.ProjectPreviewViewHolder

class ProjectPreviewAdapter(private val isMain: Boolean, private val listener: OnProjectClickListener) : BaseAdapter<ProjectPreviewModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectPreviewViewHolder {
        return ProjectPreviewViewHolder(parent, isMain, listener)
    }

    interface OnProjectClickListener {
        fun onProjectClick(projectIdx: Int)
    }
}