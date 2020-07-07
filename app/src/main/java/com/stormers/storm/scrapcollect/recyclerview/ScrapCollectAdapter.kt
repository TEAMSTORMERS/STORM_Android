package com.stormers.storm.scrapcollect.recyclerview

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder

class ScrapCollectAdapter () : BaseAdapter<ScrapCollectData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ScrapCollectData> {
        return ScrapCollectViewHolder(parent)
    }
}