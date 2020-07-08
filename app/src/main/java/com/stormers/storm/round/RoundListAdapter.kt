package com.stormers.storm.round

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder

class RoundListAdapter :  BaseAdapter<RoundDescriptionModel>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RoundDescriptionModel> {
        return RoundViewHolder(
            parent
        )
    }
}