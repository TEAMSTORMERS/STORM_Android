package com.stormers.storm.round.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.round.viewholder.RoundViewHolderForViewPager

class RoundListAdapterForViewPager :  BaseAdapter<RoundDescriptionModel>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RoundDescriptionModel> {
        return RoundViewHolderForViewPager(parent)
    }
}