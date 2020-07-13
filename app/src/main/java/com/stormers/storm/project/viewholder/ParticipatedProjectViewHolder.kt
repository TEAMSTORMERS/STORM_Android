package com.stormers.storm.project.viewholder

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.project.adapter.ParticipatedProjectListAdapter
import com.stormers.storm.project.model.ParticipatedProjectModel
import com.stormers.storm.ui.ParticipatedProjectListActivity
import com.stormers.storm.util.MetricsUtil
import kotlinx.android.synthetic.main.item_participated_projects_list.view.*

class ParticipatedProjectViewHolder(parent: ViewGroup, private val isMain: Boolean, private val listener: ParticipatedProjectListAdapter.OnProjectClickListener) :
    BaseViewHolder<ParticipatedProjectModel>(R.layout.item_participated_projects_list, parent) {

    override fun bind(data: ParticipatedProjectModel) {
        initItemLayout(data)

        initCardImage(data)
    }

    private fun initItemLayout(data: ParticipatedProjectModel) {
        itemView.name_of_project.text = data.projectName

        if (!isMain) {
            (itemView.cardview_project_forder.layoutParams).width =
                MetricsUtil.convertDpToPixel(162f, itemView.context).toInt()
            (itemView.cardview_project_forder.layoutParams).height =
                MetricsUtil.convertDpToPixel(174f, itemView.context).toInt()
        }

        itemView.setOnClickListener {
            listener.onProjectClick(data.projectIdx)
        }
    }

    private fun initCardImage(data: ParticipatedProjectModel) {
        when (data.projectCard.size) {
            0 -> return
            1 -> Glide.with(itemView).load(data.projectCard[0]).into(itemView.card1)
            2 -> {
                Glide.with(itemView).load(data.projectCard[0]).into(itemView.card1)
                Glide.with(itemView).load(data.projectCard[1]).into(itemView.card2)
            }
            3 -> {
                Glide.with(itemView).load(data.projectCard[0]).into(itemView.card1)
                Glide.with(itemView).load(data.projectCard[1]).into(itemView.card2)
                Glide.with(itemView).load(data.projectCard[2]).into(itemView.card3)
            }
            else -> {
                Glide.with(itemView).load(data.projectCard[0]).into(itemView.card1)
                Glide.with(itemView).load(data.projectCard[1]).into(itemView.card2)
                Glide.with(itemView).load(data.projectCard[2]).into(itemView.card3)
                Glide.with(itemView).load(data.projectCard[3]).into(itemView.card4)
            }
        }
    }
}