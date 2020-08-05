package com.stormers.storm.round.adapter


import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.round.model.FinalRoundData
import com.stormers.storm.round.viewholder.RoundUserImageViewHolder
import com.stormers.storm.round.viewholder.RoundViewHolder

class RoundUserImageAdapter () : BaseAdapter<FinalRoundData>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<FinalRoundData> {
    return RoundUserImageViewHolder(parent)
    }
}