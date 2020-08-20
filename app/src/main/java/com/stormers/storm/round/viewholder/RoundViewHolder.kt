package com.stormers.storm.round.viewholder

import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.round.adapter.RoundListAdapter
import com.stormers.storm.round.base.BaseRoundViewHolder
import com.stormers.storm.round.model.RoundModel

class RoundViewHolder(parent: ViewGroup, val listener: RoundListAdapter.OnRoundClickListener?) :
    BaseRoundViewHolder(parent, R.layout.item_round_part_detail) {

    override fun bind(data: RoundModel) {
        super.bind(data)

        listener?.let {
            baseItemView.setOnClickListener {
                listener.onRoundClick(data.roundIdx, data.roundNumber!!)
            }
        }
    }
}


