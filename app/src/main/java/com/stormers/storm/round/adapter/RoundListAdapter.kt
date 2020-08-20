package com.stormers.storm.round.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.round.viewholder.RoundViewHolder
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.round.model.RoundModel
import com.stormers.storm.ui.ParticipatedProjectDetailActivity

class RoundListAdapter(private val listener: OnRoundClickListener?) : BaseAdapter<RoundModel>() {

    private var projectName: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RoundModel> {
        return RoundViewHolder(parent, projectName, listener)
    }

    interface OnRoundClickListener {
        fun onRoundClick(roundIdx: Int, roundNo: Int)
    }
}

