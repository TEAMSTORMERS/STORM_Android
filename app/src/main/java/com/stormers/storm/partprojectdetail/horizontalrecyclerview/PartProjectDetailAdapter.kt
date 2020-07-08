package com.stormers.storm.partprojectdetail.horizontalrecyclerview

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder

class PartProjectDetailAdapter () : BaseAdapter<PartProjectDetailData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PartProjectDetailData> {
        return PartProjectDetailViewHolder(
            parent
        )
    }
}