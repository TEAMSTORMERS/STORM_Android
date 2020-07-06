package com.stormers.storm.projectcardlist.recyclerview

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder

class ProjectCardListAdapter :  BaseAdapter<ProjectCardListData>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ProjectCardListData> {
        return ProjectCardListViewHolder(parent)
    }
}