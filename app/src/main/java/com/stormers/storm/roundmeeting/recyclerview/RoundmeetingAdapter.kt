package com.stormers.storm.roundmeeting.recyclerview

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder

class RoundmeetingAdapter () : BaseAdapter<RoundmeetingData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RoundmeetingData> {
        return RoundmeetingViewHolder(parent)
    }
}