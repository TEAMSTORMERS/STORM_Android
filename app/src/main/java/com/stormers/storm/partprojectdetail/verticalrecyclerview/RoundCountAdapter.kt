package com.stormers.storm.partprojectdetail.verticalrecyclerview

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder

class RoundCountAdapter () : BaseAdapter<RoundCountData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RoundCountData> {
        return RoundCountViewHolder(
            parent
        )
    }
}