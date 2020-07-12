package com.stormers.storm.project.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.project.model.ParticipatedProjectModel
import com.stormers.storm.project.viewholder.ParticipatedProjectViewHolder
import com.stormers.storm.ui.ParticipatedProjectListActivity

class ParticipatedProjectListAdapter(private val listener: ParticipatedProjectListActivity.OnProjectClickListener) : BaseAdapter<ParticipatedProjectModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipatedProjectViewHolder {
        return ParticipatedProjectViewHolder(parent, listener)
    }
}