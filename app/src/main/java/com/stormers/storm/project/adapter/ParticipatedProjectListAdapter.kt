package com.stormers.storm.project.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.project.model.ParticipatedProjectModel
import com.stormers.storm.project.viewholder.ParticipatedProjectViewHolder

class ParticipatedProjectListAdapter : BaseAdapter<ParticipatedProjectModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipatedProjectViewHolder {
        return ParticipatedProjectViewHolder(parent)
    }
}