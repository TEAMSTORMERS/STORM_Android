package com.stormers.storm.card.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.project.viewholder.CacheCardViewHolder

class CacheCardListAdapter : BaseAdapter<CacheCardModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CacheCardModel> {
        return CacheCardViewHolder(parent)
    }
}