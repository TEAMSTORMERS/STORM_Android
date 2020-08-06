package com.stormers.storm.project.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.SimpleCardModel
import com.stormers.storm.project.model.ParticipatedProjectModel
import com.stormers.storm.project.viewholder.ParticipatedProjectViewHolder
import com.stormers.storm.ui.ParticipatedProjectListActivity

class ParticipatedProjectListAdapter(private val isMain: Boolean, private val listener: OnProjectClickListener) : BaseAdapter<ParticipatedProjectModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipatedProjectViewHolder {
        return ParticipatedProjectViewHolder(parent, isMain, listener)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ParticipatedProjectModel>, position: Int) {
        super.onBindViewHolder(holder, position)
        //Fixme: 재사용하면 스크롤 할 수록 아이템이 작아지고 중첩됨. 지금의 방법은 좋은 해결방법이 아닌듯 함.
        holder.setIsRecyclable(false)
    }

    interface OnProjectClickListener {
        fun onProjectClick(projectIdx: Int)
    }
}