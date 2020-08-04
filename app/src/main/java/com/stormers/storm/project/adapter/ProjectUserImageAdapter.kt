package com.stormers.storm.project.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.project.model.ProjectInfoForUserImageModel
import com.stormers.storm.project.viewholder.UserImageViewHolder
import com.stormers.storm.round.adapter.RoundListAdapter

class ProjectUserImageAdapter() : BaseAdapter<String>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
        return UserImageViewHolder(parent)

    }
}