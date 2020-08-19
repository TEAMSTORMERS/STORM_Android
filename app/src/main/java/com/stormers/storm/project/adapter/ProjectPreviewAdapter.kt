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

    override fun onBindViewHolder(holder: BaseViewHolder<ProjectPreviewModel>, position: Int) {
        super.onBindViewHolder(holder, position)
        //Fixme: 재사용하면 스크롤 할 수록 아이템이 작아지고 중첩됨. 지금의 방법은 좋은 해결방법이 아닌듯 함.
        //holder.setIsRecyclable(false)
    }

    interface OnProjectClickListener {
        fun onProjectClick(projectIdx: Int)
    }
}