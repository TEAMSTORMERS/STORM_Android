package com.stormers.storm.round.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.ui.ParticipatedProjectDetailActivity
import kotlinx.android.synthetic.main.item_heart_card.view.*

class RoundViewHolder (parent: ViewGroup, val listener: ParticipatedProjectDetailActivity.OnRoundClickListener?) : BaseViewHolder<RoundDescriptionModel>(R.layout.item_round_part_detail, parent) {
    val Textview_round_roundinfo = itemView.findViewById<TextView>(R.id.Textview_round_roundinfo)
    val Textview_round_goal_roundinfo = itemView.findViewById<TextView>(R.id.Textview_round_goal_roundinfo)
    val Textview_time_roundinfo = itemView.findViewById<TextView>(R.id.Textview_time_roundinfo)

    override fun bind(data : RoundDescriptionModel){
        Textview_round_roundinfo.text = data.projectTitle
        Textview_round_goal_roundinfo.text = data.roundGoal
        Textview_time_roundinfo.text = data.time

        listener?.let {
            itemView.setOnClickListener {
                //Todo: CardModel을 개편해야겠다 ~ projectIdx, roundIdx를 포함하고 있어야겠어
                data.roundIdx?.let { it1 -> listener.onRoundClick(it1) }
            }
        }
    }
}
