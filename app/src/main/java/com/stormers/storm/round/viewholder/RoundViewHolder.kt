package com.stormers.storm.round.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.project.adapter.ProjectUserImageAdapter
import com.stormers.storm.round.adapter.RoundListAdapter
import com.stormers.storm.round.model.RoundDescriptionModel
import java.lang.StringBuilder

class RoundViewHolder (parent: ViewGroup, val listener: RoundListAdapter.OnRoundClickListener?) : BaseViewHolder<RoundDescriptionModel>(R.layout.item_round_part_detail, parent) {
    val Textview_round_roundinfo = itemView.findViewById<TextView>(R.id.Textview_round_roundinfo)
    val Textview_round_goal_roundinfo = itemView.findViewById<TextView>(R.id.Textview_round_goal_roundinfo)
    val Textview_time_roundinfo = itemView.findViewById<TextView>(R.id.Textview_time_roundinfo)

    override fun bind(data : RoundDescriptionModel){

        val roundTime = StringBuilder()
        roundTime.append("총 ")
            .append(data.time)
            .append("분 소요")

        Textview_round_roundinfo.text = data.projectTitle
        Textview_round_goal_roundinfo.text = data.roundGoal
        Textview_time_roundinfo.text = roundTime.toString()

        listener?.let {
            itemView.setOnClickListener {
                listener.onRoundClick(data.projectIdx, data.roundIdx, data.roundNo)
            }
        }
    }
}


