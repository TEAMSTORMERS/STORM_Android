package com.stormers.storm.round.viewholder

import android.view.ViewGroup
import android.widget.TextView
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.round.model.RoundDescriptionModel
import java.lang.StringBuilder

class RoundViewHolderForViewPager(parent: ViewGroup) : BaseViewHolder<RoundDescriptionModel>(R.layout.item_round_info_card, parent) {
    private val textViewProjectTitleRoundInfo = itemView.findViewById<TextView>(R.id.textview_roundinfo_projectname)
    private val TextviewRoundNumRoundinfo = itemView.findViewById<TextView>(R.id.textview_roundinfo_roundnumber)
    private val TextviewRoundGoalRoundinfo = itemView.findViewById<TextView>(R.id.textview_roundinfo_purpose)
    private val TextviewTimeRoundinfo = itemView.findViewById<TextView>(R.id.textview_roundinfo_time)

    override fun bind(data: RoundDescriptionModel) {
        val roundNo = StringBuilder()
        roundNo.append("Round")
            .append(data.roundNo)

        val roundTime = StringBuilder()
        roundTime.append("총 ")
            .append(data.time)
            .append("분 소요")

        textViewProjectTitleRoundInfo.text = data.projectTitle
        TextviewRoundNumRoundinfo.text = roundNo.toString()
        TextviewRoundGoalRoundinfo.text = data.roundGoal
        TextviewTimeRoundinfo.text = roundTime.toString()

    }
}