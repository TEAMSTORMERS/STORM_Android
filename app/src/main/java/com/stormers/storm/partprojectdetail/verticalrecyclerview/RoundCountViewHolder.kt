package com.stormers.storm.partprojectdetail.verticalrecyclerview

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder

class RoundCountViewHolder (parent: ViewGroup) : BaseViewHolder<RoundCountData>(R.layout.item_round_part_detail, parent) {
    val Textview_round_roundinfo = itemView.findViewById<TextView>(R.id.Textview_round_roundinfo)
    val Textview_round_goal_roundinfo = itemView.findViewById<TextView>(R.id.Textview_round_goal_roundinfo)
    val Textview_time_roundinfo = itemView.findViewById<TextView>(R.id.Textview_time_roundinfo)

    override fun bind(data : RoundCountData){
        Textview_round_roundinfo.text = data.Textview_round_roundinfo
        Textview_round_goal_roundinfo.text = data.Textview_round_goal_roundinfo
        Textview_time_roundinfo.text = data.Textview_time_roundinfo
    }
}