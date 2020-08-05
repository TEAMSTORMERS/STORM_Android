package com.stormers.storm.round.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.round.viewholder.RoundViewHolder
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.ui.ParticipatedProjectDetailActivity

class RoundListAdapter(private val listener: OnRoundClickListener?) : BaseAdapter<RoundDescriptionModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RoundDescriptionModel> {
        return RoundViewHolder(parent, listener)
    }

    override fun getItemCount(): Int {
        return if(items.size < 6){
            items.size
        } else{
            5
        }
    }

    interface OnRoundClickListener {
        fun onRoundClick(projectIdx: Int, roundIdx: Int, roundNo: Int)
    }
}

