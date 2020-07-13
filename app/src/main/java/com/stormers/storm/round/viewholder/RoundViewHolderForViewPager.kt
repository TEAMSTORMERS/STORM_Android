package com.stormers.storm.round.viewholder

import android.view.ViewGroup
import android.widget.TextView
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.round.model.RoundDescriptionModel

class RoundViewHolderForViewPager(parent: ViewGroup) : BaseViewHolder<RoundDescriptionModel>(R.layout.item_round_info_card, parent) {
    private val textViewProjectTitleRoundInfo = itemView.findViewById<TextView>(R.id.Textview_project_title_roundinfo)
    private val TextviewRoundNumRoundinfo = itemView.findViewById<TextView>(R.id.Textview_round_roundinfo)
    private val TextviewRoundGoalRoundinfo = itemView.findViewById<TextView>(R.id.Textview_round_goal_roundinfo)
    private val TextviewTimeRoundinfo = itemView.findViewById<TextView>(R.id.Textview_time_roundinfo)

    override fun bind(data: RoundDescriptionModel) {
        textViewProjectTitleRoundInfo.text = data.projectTitle
        TextviewRoundNumRoundinfo.text = data.roundNo
        TextviewRoundGoalRoundinfo.text = data.roundGoal
        TextviewTimeRoundinfo.text = data.time

    }
}