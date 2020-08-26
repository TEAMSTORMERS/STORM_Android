package com.stormers.storm.round.viewholder

import android.view.View
import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.round.base.BaseRoundViewHolder
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.round.model.RoundModel
import kotlinx.android.synthetic.main.item_round_part_detail.view.*

class RoundViewHolderForViewPager(parent: ViewGroup, val projectName: String) :
    BaseRoundViewHolder(parent, R.layout.item_round_info_card) {

    override fun bind(data: RoundDescriptionModel) {
        super.bind(data)

        baseItemView.textview_roundinfo_projectname.run {
            visibility = View.VISIBLE
            text = projectName
        }
    }
}